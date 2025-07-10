package MuskElion.CodeGraph.gemini.controller;

import MuskElion.CodeGraph.gemini.dto.QueryRequestDTO;
import MuskElion.CodeGraph.gemini.dto.QueryResponseDTO;
import MuskElion.CodeGraph.gemini.service.GeminiService;
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

    @PostMapping("/ask")
    public ResponseEntity<QueryResponseDTO> askGemini(@RequestBody QueryRequestDTO requestDTO){
        String answer = service.getCompletion(requestDTO.getText());
        return ResponseEntity.ok(new QueryResponseDTO(answer));
    }
}
