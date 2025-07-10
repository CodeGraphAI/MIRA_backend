package MuskElion.CodeGraph.gemini.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Content {
    private List<Part> parts;
}
