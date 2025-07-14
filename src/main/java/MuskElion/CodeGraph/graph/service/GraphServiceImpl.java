package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ClassNode;
import MuskElion.CodeGraph.graph.node.FunctionNode;
import MuskElion.CodeGraph.graph.node.ModuleNode;
import MuskElion.CodeGraph.mcp.McpContextMessage;
import MuskElion.CodeGraph.mcp.service.McpPublishService;
import MuskElion.CodeGraph.parser.dto.AstNode;
import MuskElion.CodeGraph.parser.dto.ParseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 파싱 결과를 그래프에 저장하는 서비스 구현체.
 */
@Service
public class GraphServiceImpl implements GraphService {

    private final ModuleProcessor moduleProcessor;
    private final ClassProcessor classProcessor;
    private final FunctionProcessor functionProcessor;
    private final ImportRelationshipProcessor importRelationshipProcessor;
    private final InheritRelationshipProcessor inheritRelationshipProcessor;
    private final CallRelationshipProcessor callRelationshipProcessor;
    private final McpPublishService mcpPublishService;
    private final ObjectMapper objectMapper;
    private final AstToGraphMapper astToGraphMapper;

    // 생성자: 필요한 프로세서와 서비스들을 주입받습니다.
    public GraphServiceImpl(
            ModuleProcessor moduleProcessor,
            ClassProcessor classProcessor,
            FunctionProcessor functionProcessor,
            ImportRelationshipProcessor importRelationshipProcessor,
            InheritRelationshipProcessor inheritRelationshipProcessor,
            CallRelationshipProcessor callRelationshipProcessor,
            AstToGraphMapper astToGraphMapper,
            McpPublishService mcpPublishService,
            ObjectMapper objectMapper) {
        this.moduleProcessor = moduleProcessor;
        this.classProcessor = classProcessor;
        this.functionProcessor = functionProcessor;
        this.importRelationshipProcessor = importRelationshipProcessor;
        this.inheritRelationshipProcessor = inheritRelationshipProcessor;
        this.callRelationshipProcessor = callRelationshipProcessor;
        this.astToGraphMapper = astToGraphMapper;
        this.mcpPublishService = mcpPublishService;
        this.objectMapper = objectMapper;
    }

    // AST 노드 타입을 위한 Enum (매직 스트링 방지)
    private enum AstNodeType {
        PROGRAM("program"),
        CLASS_DECLARATION("class_declaration"),
        IMPORT_STATEMENT("import_statement", "import_declaration"),
        FUNCTION_DECLARATION("function_declaration", "method_declaration"),
        SUPERCLASS("superclass"),
        METHOD_INVOCATION("method_invocation", "call_expression");

        private final String[] identifiers;

        AstNodeType(String... identifiers) {
            this.identifiers = identifiers;
        }

        public boolean matches(String type) {
            for (String identifier : identifiers) {
                if (identifier.equals(type)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    @Transactional
    // 파싱 결과를 그래프에 저장하고 MCP 이벤트를 발행합니다.
    public void saveParsedResult(ParseResult parseResult) {
        String filePath = parseResult.getFilePath();
        String status = "success";
        try {
            ModuleNode moduleNode = moduleProcessor.findOrCreate(parseResult);
            processAstNode(parseResult.getRootNode(), moduleNode, filePath);
        } catch (Exception e) {
            status = "failed";
            throw e; // 예외 발생 시 상태를 실패로 변경 후 다시 던집니다.
        } finally {
            try {
                // MCP 메시지 페이로드 생성
                java.util.Map<String, String> payloadMap = new java.util.HashMap<>();
                payloadMap.put("filePath", filePath);
                payloadMap.put("status", status);
                String payload = objectMapper.writeValueAsString(payloadMap);
                
                // MCP 메시지 발행
                McpContextMessage message = new McpContextMessage(
                        "GRAPH_UPDATED",
                        "GraphService",
                        "All", // 모든 구독자에게 전송
                        payload,
                        System.currentTimeMillis()
                );
                mcpPublishService.publish(message);
            } catch (Exception e) {
                // MCP 메시지 발행 실패는 주 트랜잭션에 영향을 주지 않습니다.
            }
        }
    }

    // AST 노드를 처리하여 그래프에 반영합니다.
    private void processAstNode(AstNode astNode, Object parentNode, String filePath) {
        if (astNode == null) {
            return;
        }

        if (AstNodeType.CLASS_DECLARATION.matches(astNode.getType())) {
            processClassNode(astNode, parentNode, filePath);
        } else if (AstNodeType.IMPORT_STATEMENT.matches(astNode.getType())) {
            processImportNode(astNode, parentNode, filePath);
        } else if (AstNodeType.FUNCTION_DECLARATION.matches(astNode.getType())) {
            processFunctionNode(astNode, parentNode, filePath);
        } else {
            processChildren(astNode, parentNode, filePath);
        }
    }

    // 클래스 노드를 처리합니다.
    private void processClassNode(AstNode astNode, Object parentNode, String filePath) {
        ClassNode classNode = classProcessor.findOrCreate(astNode, filePath);
        classNode.setStartLine(astNode.getStartPosition().getRow());
        classNode.setEndLine(astNode.getEndPosition().getRow());

        if (parentNode instanceof ModuleNode moduleNode) {
            moduleNode.addDefinedClass(classNode);
            moduleProcessor.save(moduleNode);
        }

        if (astNode.getChildren() != null) {
            for (AstNode child : astNode.getChildren()) {
                if (AstNodeType.SUPERCLASS.matches(child.getType())) {
                    inheritRelationshipProcessor.process(child, classNode, filePath);
                } else if (AstNodeType.FUNCTION_DECLARATION.matches(child.getType())) {
                    processFunctionNode(child, classNode, filePath);
                } else {
                    processAstNode(child, classNode, filePath);
                }
            }
        }
    }

    // 함수 노드를 처리합니다.
    private void processFunctionNode(AstNode astNode, Object parentNode, String filePath) {
        FunctionNode functionNode = functionProcessor.findOrCreate(astNode, filePath);
        functionNode.setStartLine(astNode.getStartPosition().getRow());
        functionNode.setEndLine(astNode.getEndPosition().getRow());

        if (parentNode instanceof ModuleNode moduleNode) {
            moduleNode.addDefinedFunction(functionNode);
            moduleProcessor.save(moduleNode);
        } else if (parentNode instanceof ClassNode classNode) {
            classNode.addDefinedFunction(functionNode);
            classProcessor.save(classNode);
        }
        functionProcessor.save(functionNode);

        if (astNode.getChildren() != null) {
            for (AstNode child : astNode.getChildren()) {
                if (AstNodeType.METHOD_INVOCATION.matches(child.getType())) {
                    callRelationshipProcessor.process(child, functionNode, filePath);
                } else {
                    processAstNode(child, functionNode, filePath);
                }
            }
        }
    }

    // 임포트 노드를 처리합니다.
    private void processImportNode(AstNode astNode, Object parentNode, String filePath) {
        if (parentNode instanceof ModuleNode moduleNode) {
            importRelationshipProcessor.process(astNode, moduleNode, filePath);
        }
    }

    // 자식 노드들을 재귀적으로 처리합니다.
    private void processChildren(AstNode astNode, Object parentNode, String filePath) {
        if (astNode.getChildren() != null) {
            for (AstNode child : astNode.getChildren()) {
                processAstNode(child, parentNode, filePath);
            }
        }
    }
}