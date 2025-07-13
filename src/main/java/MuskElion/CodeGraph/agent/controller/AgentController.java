package MuskElion.CodeGraph.agent.controller;

import MuskElion.CodeGraph.agent.dto.AgentQueryRequest;
import MuskElion.CodeGraph.agent.dto.AgentQueryResponse;
import MuskElion.CodeGraph.agent.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @PostMapping("/query")
    public ResponseEntity<AgentQueryResponse> queryAgent(@RequestBody AgentQueryRequest request) {
        // TODO: AgentService를 호출하여 쿼리 처리 로직 구현
        AgentQueryResponse response = agentService.processQuery(request);
        return ResponseEntity.ok(response);
    }
}
