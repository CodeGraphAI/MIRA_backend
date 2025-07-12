package MuskElion.CodeGraph.mcp.service;

import MuskElion.CodeGraph.mcp.McpContextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class McpPublishService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic mcpTopic;

    /**
     * MCP 컨텍스트 메시지를 Redis 채널로 발행합니다.
     * @param message 발행할 메시지 객체
     */
    public void publish(McpContextMessage message) {
        redisTemplate.convertAndSend(mcpTopic.getTopic(), message);
    }
}
