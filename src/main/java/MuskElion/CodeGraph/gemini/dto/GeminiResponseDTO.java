package MuskElion.CodeGraph.gemini.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GeminiResponseDTO {
    private List<Candidate> candidates;
}
