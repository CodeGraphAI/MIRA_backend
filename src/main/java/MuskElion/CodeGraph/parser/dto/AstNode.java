package MuskElion.CodeGraph.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AstNode {
    private String type;
    private String value;
    private Position startPosition;
    private Position endPosition;
    private List<AstNode> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AstNode astNode = (AstNode) o;
        return Objects.equals(type, astNode.type) &&
               Objects.equals(value, astNode.value) &&
               Objects.equals(startPosition, astNode.startPosition) &&
               Objects.equals(endPosition, astNode.endPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, startPosition, endPosition);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private int row;
        private int column;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return row == position.row &&
                   column == position.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }
}
