package MuskElion.CodeGraph.gemini.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class QueryResponseDTO {
    private String result;

    public QueryResponseDTO(String answer) {
        this.result = answer;
    }
}
