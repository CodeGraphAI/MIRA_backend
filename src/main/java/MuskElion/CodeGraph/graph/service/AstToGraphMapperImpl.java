package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ClassNode;
import MuskElion.CodeGraph.graph.node.FunctionNode;
import MuskElion.CodeGraph.graph.node.ModuleNode;
import MuskElion.CodeGraph.graph.relationship.CallsRelationship;
import MuskElion.CodeGraph.graph.relationship.ImportsRelationship;
import MuskElion.CodeGraph.graph.relationship.InheritsRelationship;
import MuskElion.CodeGraph.parser.dto.AstNode;
import MuskElion.CodeGraph.parser.dto.ParseResult;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * AST 노드를 그래프 노드 및 관계로 매핑하는 구현체.
 */
@Component
public class AstToGraphMapperImpl implements AstToGraphMapper {

    @Override
    public ModuleNode mapToModuleNode(ParseResult parseResult) {
        return ModuleNode.builder()
                .id(UUID.randomUUID().toString())
                .filePath(parseResult.getFilePath())
                .language(parseResult.getLanguage())
                .name(extractModuleName(parseResult.getFilePath()))
                .build();
    }

    @Override
    public ClassNode mapToClassNode(AstNode classAstNode, String filePath) {
        String className = extractNameFromAstNode(classAstNode);
        if (className == null) return null;
        int startLine = classAstNode.getStartPosition().getRow();
        int endLine = classAstNode.getEndPosition().getRow();

        return ClassNode.builder()
                .id(UUID.randomUUID().toString())
                .name(className)
                .filePath(filePath)
                .startLine(startLine)
                .endLine(endLine)
                .build();
    }

    @Override
    public FunctionNode mapToFunctionNode(AstNode functionAstNode, String filePath) {
        String functionName = extractNameFromAstNode(functionAstNode);
        if (functionName == null) return null;
        int startLine = functionAstNode.getStartPosition().getRow();
        int endLine = functionAstNode.getEndPosition().getRow();

        return FunctionNode.builder()
                .id(UUID.randomUUID().toString())
                .name(functionName)
                .filePath(filePath)
                .startLine(startLine)
                .endLine(endLine)
                .build();
    }

    @Override
    public ImportsRelationship mapToImportRelationship(AstNode importAstNode, ModuleNode sourceModule, String filePath) {
        String importedName = extractNameFromAstNode(importAstNode);
        if (importedName == null) return null;
        int importLine = importAstNode.getStartPosition().getRow();

        return ImportsRelationship.builder()
                .id(UUID.randomUUID().toString())
                .importName(importedName)
                .lineNumber(importLine)
                .build();
    }

    @Override
    public InheritsRelationship mapToInheritRelationship(AstNode superclassAstNode, ClassNode childClass, String filePath) {
        String parentClassName = extractNameFromAstNode(superclassAstNode);
        if (parentClassName == null) return null;

        return InheritsRelationship.builder()
                .id(UUID.randomUUID().toString())
                .build();
    }

    @Override
    public CallsRelationship mapToCallRelationship(AstNode callAstNode, FunctionNode callingFunction, String filePath) {
        String calledFunctionName = extractNameFromAstNode(callAstNode);
        if (calledFunctionName == null) return null;
        int callSiteLine = callAstNode.getStartPosition().getRow();

        return CallsRelationship.builder()
                .id(UUID.randomUUID().toString())
                .callSiteLine(callSiteLine)
                .build();
    }

    @Override
    public String extractNameFromAstNode(AstNode astNode) {
        if (astNode.getValue() != null && !astNode.getValue().isEmpty()) {
            return astNode.getValue();
        }
        if (astNode.getChildren() != null) {
            for (AstNode child : astNode.getChildren()) {
                if ("identifier".equals(child.getType()) || "qualified_identifier".equals(child.getType())) {
                    return child.getValue();
                }
                String name = extractNameFromAstNode(child);
                if (name != null && !name.isEmpty()) {
                    return name;
                }
            }
        }
        return null;
    }

    private String extractModuleName(String filePath) {
        int lastSlash = filePath.lastIndexOf("/");
        int lastDot = filePath.lastIndexOf(".");
        if (lastSlash == -1) lastSlash = 0;
        if (lastDot == -1 || lastDot < lastSlash) lastDot = filePath.length();

        return filePath.substring(lastSlash + 1, lastDot);
    }
}
