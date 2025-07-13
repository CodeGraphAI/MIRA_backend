package MuskElion.CodeGraph.agent.service;

import MuskElion.CodeGraph.agent.dto.AgentQueryRequest;
import MuskElion.CodeGraph.agent.dto.AgentQueryResponse;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    public AgentQueryResponse processQuery(AgentQueryRequest request) {
        // TODO: 쿼리 의도 분석, 로컬 그래프 조회, AI 연동 등 핵심 로직 구현
        // 현재는 빈 응답 반환
        return new AgentQueryResponse("아직 구현되지 않은 기능입니다.", null, null, null);
    }
}
