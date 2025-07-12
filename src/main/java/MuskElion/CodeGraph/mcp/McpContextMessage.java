package MuskElion.CodeGraph.mcp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpContextMessage {
    private String eventType;
    private String sourceModel;
    private String targetModel;
    private String payload;
    private long timestamp;
}
