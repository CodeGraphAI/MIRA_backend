package MuskElion.CodeGraph.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentQueryRequest {
    private String query;
    private String clientType; // "CLI" 또는 "WEB"
}
