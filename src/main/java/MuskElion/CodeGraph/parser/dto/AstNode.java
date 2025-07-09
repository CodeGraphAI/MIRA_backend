package MuskElion.CodeGraph.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AstNode {
    private String type;
    private String value; // For literal nodes like identifiers, numbers, strings
    private Position startPosition;
    private Position endPosition;
    private List<AstNode> children;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private int row;
        private int column;
    }
}
