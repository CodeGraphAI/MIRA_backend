package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.parser.dto.AstNode;
import MuskElion.CodeGraph.parser.dto.ParseResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GraphService 테스트 클래스.
 */
class GraphServiceTest {

    private GraphServiceImpl graphService;
    private ModuleProcessorStub moduleProcessor;
    private ClassProcessorStub classProcessor;
    private FunctionProcessorStub functionProcessor;
    private ImportRelationshipProcessorStub importRelationshipProcessor;
    private InheritRelationshipProcessorStub inheritRelationshipProcessor;
    private CallRelationshipProcessorStub callRelationshipProcessor;
    private McpPublishServiceStub mcpPublishService;

    private ParseResult sampleParseResult;

    @BeforeEach
    void setUp() {
        // 스텁 초기화
        moduleProcessor = new ModuleProcessorStub();
        classProcessor = new ClassProcessorStub();
        functionProcessor = new FunctionProcessorStub();
        importRelationshipProcessor = new ImportRelationshipProcessorStub();
        inheritRelationshipProcessor = new InheritRelationshipProcessorStub();
        callRelationshipProcessor = new CallRelationshipProcessorStub();
        AstToGraphMapperStub astToGraphMapper = new AstToGraphMapperStub();
        mcpPublishService = new McpPublishServiceStub();
        ObjectMapper objectMapper = new ObjectMapper(); // 테스트용 ObjectMapper 인스턴스 생성

        // GraphServiceImpl에 스텁 주입
        graphService = new GraphServiceImpl(
                moduleProcessor,
                classProcessor,
                functionProcessor,
                importRelationshipProcessor,
                inheritRelationshipProcessor,
                callRelationshipProcessor,
                astToGraphMapper,
                mcpPublishService,
                objectMapper // ObjectMapper 주입
        );

        // 샘플 ParseResult 생성
        AstNode.Position pos1 = new AstNode.Position(1, 1);
        AstNode.Position pos2 = new AstNode.Position(2, 10);
        AstNode.Position pos4 = new AstNode.Position(4, 30);
        AstNode.Position pos5 = new AstNode.Position(5, 40);
        AstNode.Position pos10 = new AstNode.Position(10, 1);
        AstNode.Position pos11 = new AstNode.Position(11, 1);
        AstNode.Position pos12 = new AstNode.Position(12, 10);

        // 메서드 호출: anotherMethod()
        AstNode methodCallNode = new AstNode("method_invocation", "anotherMethod", pos5, pos5, Collections.emptyList());

        // 메서드: myMethod()
        AstNode myMethodNode = new AstNode("method_declaration", "myMethod", pos4, pos5, List.of(methodCallNode));

        // 클래스: MyClass extends BaseClass
        AstNode superClassNode = new AstNode("superclass", "BaseClass", pos2, pos2, Collections.emptyList());
        AstNode classDeclarationNode = new AstNode("class_declaration", "MyClass", pos2, pos10, Arrays.asList(superClassNode, myMethodNode));

        // 임포트: java.util.List
        AstNode importDeclarationNode = new AstNode("import_declaration", "java.util.List", pos1, pos1, Collections.emptyList());

        // 최상위 함수: anotherTopLevelFunction()
        AstNode anotherTopLevelFunctionNode = new AstNode("function_declaration", "anotherTopLevelFunction", pos11, pos12, Collections.emptyList());

        // 루트 노드 (파일 내용 표현)
        AstNode rootNode = new AstNode("program", null, pos1, pos12, Arrays.asList(importDeclarationNode, classDeclarationNode, anotherTopLevelFunctionNode));

        sampleParseResult = new ParseResult("/path/to/MyClass.java", "java", rootNode);
    }

    @Test
    void testSaveParsedResult_basicFlow() {
        // 실행
        graphService.saveParsedResult(sampleParseResult);

        // 검증
        // moduleProcessor.findOrCreate 및 save 호출 확인
        assertNotNull(moduleProcessor.lastSavedModule, "모듈이 저장되어야 합니다.");
        assertNotNull(moduleProcessor.lastSavedModule.getDefinedClasses());
        assertFalse(moduleProcessor.lastSavedModule.getDefinedClasses().isEmpty(), "모듈은 하나 이상의 클래스를 정의해야 합니다.");
        assertNotNull(moduleProcessor.lastSavedModule.getDefinedFunctions());
        assertFalse(moduleProcessor.lastSavedModule.getDefinedFunctions().isEmpty(), "모듈은 하나 이상의 함수를 정의해야 합니다.");

        // classProcessor.findOrCreate 및 save 호출 확인
        assertNotNull(classProcessor.lastSavedClass, "클래스가 저장되어야 합니다.");
        assertNotNull(classProcessor.lastSavedClass.getDefinedFunctions());
        assertFalse(classProcessor.lastSavedClass.getDefinedFunctions().isEmpty(), "클래스는 하나 이상의 함수를 정의해야 합니다.");

        // functionProcessor.findOrCreate 및 save 호출 확인
        assertNotNull(functionProcessor.lastSavedFunction, "함수가 저장되어야 합니다.");

        // 관계 프로세서 호출 확인
        assertNotNull(importRelationshipProcessor, "임포트 관계 프로세서는 null이 아니어야 합니다.");
        assertTrue(importRelationshipProcessor.processCalled, "임포트 관계 프로세서가 호출되어야 합니다.");
        assertTrue(inheritRelationshipProcessor.processCalled, "상속 관계 프로세서가 호출되어야 합니다.");
        assertTrue(callRelationshipProcessor.processCalled, "호출 관계 프로세서가 호출되어야 합니다.");

        // MCP 메시지 발행 확인
        assertTrue(mcpPublishService.publishCalled, "MCP 메시지가 발행되어야 합니다.");
        assertNotNull(mcpPublishService.lastPublishedMessage, "발행된 MCP 메시지가 null이 아니어야 합니다.");
        assertEquals("GRAPH_UPDATED", mcpPublishService.lastPublishedMessage.getEventType(), "이벤트 타입이 GRAPH_UPDATED여야 합니다.");
        assertEquals("GraphService", mcpPublishService.lastPublishedMessage.getSourceModel(), "소스 모델이 GraphService여야 합니다.");

        // 페이로드 내용 검증
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            java.util.Map<String, String> payloadMap = objectMapper.readValue(mcpPublishService.lastPublishedMessage.getPayload(), new TypeReference<>() {
            });
            assertEquals(sampleParseResult.getFilePath(), payloadMap.get("filePath"), "페이로드의 파일 경로가 일치해야 합니다.");
            assertEquals("success", payloadMap.get("status"), "페이로드의 상태가 success여야 합니다.");
        } catch (Exception e) {
            fail("MCP 페이로드 파싱 중 오류 발생: " + e.getMessage());
        }
    }
}
