package MuskElion.CodeGraph.parser.controller;

import MuskElion.CodeGraph.graph.service.GraphService; // GraphService import 추가
import MuskElion.CodeGraph.parser.dto.ParseResult;
import MuskElion.CodeGraph.parser.dto.AstNode;
import MuskElion.CodeGraph.parser.service.ParserServiceStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.Mockito; // Mockito import 추가

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * ParserController 테스트 클래스.
 */
class ParserControllerTest {

    private ParserController parserController;
    private ParserServiceStub parserServiceStub;
    private GraphService mockGraphService; // Mock 객체 선언

    @BeforeEach
    void setUp() {
        mockGraphService = Mockito.mock(GraphService.class); // Mock 객체 초기화
        parserServiceStub = new ParserServiceStub(mockGraphService); // GraphService Mock 주입
        parserController = new ParserController(parserServiceStub);
    }

    @Test
    void testParseCode_success() {
        // Given
        AstNode.Position pos = new AstNode.Position(1, 1);
        AstNode rootNode = new AstNode("program", null, pos, pos, Collections.emptyList());
        ParseResult parseResult = new ParseResult("/path/to/test.java", "java", rootNode);

        parserServiceStub.processResultToReturn = true; // ParserServiceStub이 true를 반환하도록 설정

        // When
        ResponseEntity<String> response = parserController.receiveParseResult(parseResult);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Parse result for '/path/to/test.java' received and processed successfully.", response.getBody());
    }

    @Test
    void testParseCode_failure() {
        // Given - 유효하지 않은 입력 시뮬레이션
        AstNode.Position pos = new AstNode.Position(1, 1);
        AstNode rootNode = new AstNode("program", null, pos, pos, Collections.emptyList());
        ParseResult parseResult = new ParseResult(null, "java", rootNode); // filePath is null

        // When
        ResponseEntity<String> response = parserController.receiveParseResult(parseResult);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid parse result: filePath, language, and rootNode are required.", response.getBody());
    }

    @Test
    void testParseCode_internalServerError() {
        // Given - ParserService에서 내부 서버 오류 시뮬레이션
        AstNode.Position pos = new AstNode.Position(1, 1);
        AstNode rootNode = new AstNode("program", null, pos, pos, Collections.emptyList());
        ParseResult parseResult = new ParseResult("/path/to/test.java", "java", rootNode);

        parserServiceStub.processResultToReturn = false; // ParserServiceStub이 false를 반환하도록 설정

        // When
        ResponseEntity<String> response = parserController.receiveParseResult(parseResult);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to process parse result for '/path/to/test.java'. See server logs for details.", response.getBody());
    }
}