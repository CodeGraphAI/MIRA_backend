package MuskElion.CodeGraph.gemini.controller;

import MuskElion.CodeGraph.gemini.dto.QueryRequestDTO;
import MuskElion.CodeGraph.gemini.dto.QueryResponseDTO;
import MuskElion.CodeGraph.gemini.service.GeminiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gemini")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService service;

    @Operation(
            summary = "질문하기",
            description = "질문 텍스트를 입력하면 Gemini AI의 답변을 반환합니다."
                    ,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공",
                            content = @Content(schema = @Schema(implementation = QueryResponseDTO.class))
                    )
            }
    )
    @PostMapping("/ask")
    public ResponseEntity<QueryResponseDTO> askGemini(@RequestBody QueryRequestDTO requestDTO){
        String answer = service.getCompletion(requestDTO.getText());
        return ResponseEntity.ok(new QueryResponseDTO(answer));
    }
}
