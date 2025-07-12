package MuskElion.CodeGraph.mcp.config;

import MuskElion.CodeGraph.mcp.service.McpSubscribeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // MCP 메시지를 구독할 채널(토픽) 정의
    @Bean
    public ChannelTopic mcpTopic() {
        return new ChannelTopic("mcp-channel");
    }

    // Redis 메시지 리스너 컨테이너 설정
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter,
            ChannelTopic mcpTopic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, mcpTopic);
        return container;
    }

    // 메시지를 수신하고 처리할 구독 서비스(McpSubscribeService)를 리스너로 등록
    @Bean
    public MessageListenerAdapter listenerAdapter(McpSubscribeService subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    // Redis에 데이터를 쓰고 읽는 데 사용할 RedisTemplate 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        // McpContextMessage DTO를 JSON 형태로 직렬화/역직렬화
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}
