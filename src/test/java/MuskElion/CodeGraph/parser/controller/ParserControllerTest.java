package MuskElion.CodeGraph.parser.controller;

import MuskElion.CodeGraph.parser.dto.AstNode;
import MuskElion.CodeGraph.parser.dto.ParseResult;
import MuskElion.CodeGraph.parser.service.ParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParserController.class)
class ParserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParserService parserService;

    @Autowired
    private ObjectMapper objectMapper; // JSON 직렬화를 위해 필요

    private ParseResult createValidParseResult() {
        ParseResult parseResult = new ParseResult();
        parseResult.setFilePath("/test/path/to/file.java");
        parseResult.setLanguage("java");
        AstNode rootNode = new AstNode();
        rootNode.setType("program");
        rootNode.setStartPosition(new AstNode.Position(0, 0));
        rootNode.setEndPosition(new AstNode.Position(10, 0));
        parseResult.setRootNode(rootNode);
        return parseResult;
    }

    @Test
    @DisplayName("유효한 파싱 결과 수신 시 200 OK 반환 테스트")
    void receiveParseResult_validInput_returns200Ok() throws Exception {
        // Given
        ParseResult validParseResult = createValidParseResult();
        when(parserService.processParseResult(any(ParseResult.class))).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/parser/parse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validParseResult)))
                .andExpect(status().isOk())
                .andExpect(content().string("Parse result for '" + validParseResult.getFilePath() + "' received and processed successfully."));
    }

    @Test
    @DisplayName("유효하지 않은 파싱 결과 (filePath 누락) 수신 시 400 Bad Request 반환 테스트")
    void receiveParseResult_missingFilePath_returns400BadRequest() throws Exception {
        // Given
        ParseResult invalidParseResult = createValidParseResult();
        invalidParseResult.setFilePath(null); // filePath 누락

        // When & Then
        mockMvc.perform(post("/api/parser/parse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidParseResult)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid parse result: filePath, language, and rootNode are required."));
    }

    @Test
    @DisplayName("유효하지 않은 파싱 결과 (rootNode 누락) 수신 시 400 Bad Request 반환 테스트")
    void receiveParseResult_missingRootNode_returns400BadRequest() throws Exception {
        // Given
        ParseResult invalidParseResult = createValidParseResult();
        invalidParseResult.setRootNode(null); // rootNode 누락

        // When & Then
        mockMvc.perform(post("/api/parser/parse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidParseResult)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid parse result: filePath, language, and rootNode are required."));
    }

    @Test
    @DisplayName("ParserService 처리 실패 시 500 Internal Server Error 반환 테스트")
    void receiveParseResult_serviceFails_returns500InternalServerError() throws Exception {
        // Given
        ParseResult validParseResult = createValidParseResult();
        when(parserService.processParseResult(any(ParseResult.class))).thenReturn(false); // 서비스 처리 실패

        // When & Then
        mockMvc.perform(post("/api/parser/parse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validParseResult)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to process parse result for '" + validParseResult.getFilePath() + "'. See server logs for details."));
    }
}
