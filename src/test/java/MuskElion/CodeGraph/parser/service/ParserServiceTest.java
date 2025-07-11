package MuskElion.CodeGraph.parser.service;

import MuskElion.CodeGraph.parser.dto.AstNode;
import MuskElion.CodeGraph.parser.dto.ParseResult;
import MuskElion.CodeGraph.graph.service.GraphService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito; // Mockito import 추가

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any; // any() 메서드 import
import static org.mockito.Mockito.doNothing; // doNothing() 메서드 import
import static org.mockito.Mockito.doThrow; // doThrow() 메서드 import

/**
 * ParserService 테스트 클래스.
 */
class ParserServiceTest {

    private ParserService parserService;
    private GraphService mockGraphService; // Mock 객체 선언

    @BeforeEach
    void setUp() {
        mockGraphService = Mockito.mock(GraphService.class); // Mock 객체 초기화
        parserService = new ParserService(mockGraphService);
    }

    @Test
    void testProcessParseResult_success() throws Exception {
        // Given
        AstNode.Position pos = new AstNode.Position(1, 1);
        AstNode rootNode = new AstNode("program", null, pos, pos, Collections.emptyList());
        ParseResult parseResult = new ParseResult("/path/to/test.java", "java", rootNode);

        // mockGraphService.saveParsedResult가 호출될 때 아무것도 하지 않도록 설정
        doNothing().when(mockGraphService).saveParsedResult(any(ParseResult.class));

        // When
        boolean result = parserService.processParseResult(parseResult);

        // Then
        assertTrue(result, "processParseResult는 성공 시 true를 반환해야 합니다.");
    }

    @Test
    void testProcessParseResult_failure() throws Exception {
        // Given
        AstNode.Position pos = new AstNode.Position(1, 1);
        AstNode rootNode = new AstNode("program", null, pos, pos, Collections.emptyList());
        ParseResult parseResult = new ParseResult("/path/to/invalid.java", "java", rootNode);

        // mockGraphService.saveParsedResult 호출 시 예외를 발생시키도록 설정
        doThrow(new RuntimeException("Simulated GraphService error")).when(mockGraphService).saveParsedResult(any(ParseResult.class));

        // When
        boolean result = parserService.processParseResult(parseResult);

        // Then
        assertTrue(!result, "processParseResult는 실패 시 false를 반환해야 합니다.");
    }
}