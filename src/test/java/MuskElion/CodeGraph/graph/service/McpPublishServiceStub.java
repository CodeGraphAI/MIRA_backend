package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.mcp.McpContextMessage;
import MuskElion.CodeGraph.mcp.service.McpPublishService;

/**
 * McpPublishService의 테스트 스텁.
 */
public class McpPublishServiceStub extends McpPublishService {

    public boolean publishCalled = false;
    public McpContextMessage lastPublishedMessage = null;

    public McpPublishServiceStub() {
        // RedisTemplate과 ChannelTopic은 부모 생성자에서 필요하지만, 스텁에서는 사용하지 않으므로 null 전달
        // 실제 테스트에서는 이 스텁이 RedisTemplate과 ChannelTopic을 사용하지 않음을 보장해야 함
        super(null, null);
    }

    @Override
    public void publish(McpContextMessage message) {
        this.publishCalled = true;
        this.lastPublishedMessage = message;
        System.out.println("McpPublishServiceStub: Message published: " + message);
    }
}
