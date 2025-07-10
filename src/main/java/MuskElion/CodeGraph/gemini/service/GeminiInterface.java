package MuskElion.CodeGraph.gemini.service;

import MuskElion.CodeGraph.gemini.dto.GeminiRequestDTO;
import MuskElion.CodeGraph.gemini.dto.GeminiResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/v1beta/models/")
public interface GeminiInterface {

    @PostExchange("{model}:generateContent")
    GeminiResponseDTO getCompletion(
            @PathVariable String model,
            @RequestBody GeminiRequestDTO request
    );
}
