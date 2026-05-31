package com.lijinan.aiagent.controller;

import com.lijinan.aiagent.agent.LijinanManus;
import com.lijinan.aiagent.app.WorkApp;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private WorkApp workApp;

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;

    /**
     * 同步调用 AI 工作助手应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/work_app/chat/sync")
    public String doChatWithWorkAppSync(String message, String chatId) {
        return workApp.doChat(message, chatId);
    }

    /**
     * SSE 流式调用 AI 工作助手应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/work_app/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithWorkAppSSE(String message, String chatId) {
        return workApp.doChatByStream(message, chatId);
    }

    /**
     * SSE 流式调用 AI 工作助手应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/work_app/chat/server_sent_event")
    public Flux<ServerSentEvent<String>> doChatWithWorkAppServerSentEvent(String message, String chatId) {
        return workApp.doChatByStream(message, chatId)
                .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }

    /**
     * SSE 流式调用 AI 工作助手应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/work_app/chat/sse_emitter")
    public SseEmitter doChatWithWorkAppServerSseEmitter(String message, String chatId) {
        // 创建一个超时时间较长的 SseEmitter
        SseEmitter sseEmitter = new SseEmitter(180000L); // 3 分钟超时
        // 获取 Flux 响应式数据流并且直接通过订阅推送给 SseEmitter
        workApp.doChatByStream(message, chatId)
                .subscribe(chunk -> {
                    try {
                        sseEmitter.send(chunk);
                    } catch (IOException e) {
                        sseEmitter.completeWithError(e);
                    }
                }, sseEmitter::completeWithError, sseEmitter::complete);
        // 返回
        return sseEmitter;
    }

    /**
     * 流式调用 Manus 超级智能体
     *
     * @param message
     * @return
     */
    @GetMapping("/manus/chat")
    public SseEmitter doChatWithManus(String message) {
        LijinanManus lijinanManus = new LijinanManus(allTools, dashscopeChatModel);
        return lijinanManus.runStream(message);
    }
}
