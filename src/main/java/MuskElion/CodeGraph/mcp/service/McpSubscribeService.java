package MuskElion.CodeGraph.mcp.service;

import MuskElion.CodeGraph.mcp.McpContextMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class McpSubscribeService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Redis 채널로부터 메시지를 수신했을 때 호출되는 메서드.
     * @param message 수신된 메시지 (JSON 문자열)
     */
    public void onMessage(String message) {
        try {
            McpContextMessage contextMessage = objectMapper.readValue(message, McpContextMessage.class);
            log.info("Received MCP message: {}", contextMessage);
            // TODO: 수신된 메시지를 기반으로 실제 비즈니스 로직 처리
        } catch (Exception e) {
            log.error("Failed to parse MCP message: {}", message, e);
        }
    }
}
