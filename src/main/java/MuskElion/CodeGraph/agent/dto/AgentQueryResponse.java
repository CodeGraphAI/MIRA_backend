package MuskElion.CodeGraph.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentQueryResponse {
    private String summaryText; // CLI에 출력될 간결한 요약 텍스트
    private Object cliStructuredData; // CLI에서 rich 라이브러리로 표나 트리를 그릴 데이터
    private Object webVisualizationData; // 웹 UI에서 그래프를 그릴 풍부한 노드/엣지 데이터
    private String message; // 에러 메시지 등
}
