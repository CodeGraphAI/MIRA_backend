package MuskElion.CodeGraph.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParseResult {
    private String filePath;
    private String language;
    private AstNode rootNode;
}
