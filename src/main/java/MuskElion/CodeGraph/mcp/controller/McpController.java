package MuskElion.CodeGraph.mcp.controller;

import MuskElion.CodeGraph.mcp.McpContextMessage;
import MuskElion.CodeGraph.mcp.service.McpPublishService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mcp")
@RequiredArgsConstructor
public class McpController {

    private final McpPublishService publishService;

    @Operation(
            summary = "MCP 메시지 발행 테스트",
            description = "테스트용 MCP 메시지를 Redis 채널로 발행합니다."
    )
    @PostMapping("/publish")
    public String publishMessage(@RequestBody McpContextMessage message) {
        publishService.publish(message);
        return "Message published successfully to mcp-channel";
    }
}
