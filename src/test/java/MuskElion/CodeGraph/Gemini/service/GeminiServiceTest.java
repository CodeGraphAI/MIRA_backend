package MuskElion.CodeGraph.Gemini.service;

import MuskElion.CodeGraph.gemini.dto.*;
import MuskElion.CodeGraph.gemini.service.GeminiInterface;
import MuskElion.CodeGraph.gemini.service.GeminiService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class GeminiServiceTest {
    @Test
    void getCompletionTest() {
        GeminiInterface mockInterface = mock(GeminiInterface.class);
        GeminiService mockService = new GeminiService(mockInterface);

        TextPart textPart = new TextPart("안산 맛집 추천 결과!");
        Content content = new Content(List.of(textPart));
        Candidate candidate = new Candidate(content, "STOP", 0);
        GeminiResponseDTO responseDTO = new GeminiResponseDTO(List.of(candidate));

        when(mockInterface.getCompletion(anyString(), any(GeminiRequestDTO.class)))
                .thenReturn(responseDTO);

        String text = mockService.getCompletion("안산 맛집 추천해줘");
        assertEquals("안산 맛집 추천 결과!", text);
    }

    @Test
    void testEmptyParts() {
        GeminiInterface mockInterface = mock(GeminiInterface.class);
        GeminiService mockService = new GeminiService(mockInterface);

        // candidates는 있는데, parts가 비어 있는 경우
        Content content = new Content(List.of());
        Candidate candidate = new Candidate(content, "STOP", 0);
        GeminiResponseDTO response = new GeminiResponseDTO(List.of(candidate));
        when(mockInterface.getCompletion(anyString(), any(GeminiRequestDTO.class)))
                .thenReturn(response);

        String text = mockService.getCompletion("질문");
        assertNull(text, "parts가 비어 있으면 결과는 null이어야 함");
    }
    @Test
    void testEmptyCandidates() {
        GeminiInterface mockInterface = mock(GeminiInterface.class);
        GeminiService mockService = new GeminiService(mockInterface);

        // candidates 리스트가 빈 경우
        GeminiResponseDTO emptyResponse = new GeminiResponseDTO(List.of());
        when(mockInterface.getCompletion(anyString(), any(GeminiRequestDTO.class)))
                .thenReturn(emptyResponse);

        String text = mockService.getCompletion("질문");
        assertNull(text, "candidates가 비어 있으면 결과는 null이어야 함");
    }


}