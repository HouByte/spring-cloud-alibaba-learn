package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.stream.DefaultSendService;
import cn.flowboot.e.commerce.stream.custom.CustomSendService;
import cn.flowboot.e.commerce.vo.StreamMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/10
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("stream")
public class StreamController {

    private final DefaultSendService defaultSendService;

    private final CustomSendService customSendService;

    /**
     * <h2>发送 Steam 消息</h2>
     * */
    @GetMapping("/send-message")
    public void sendMessage(){
        defaultSendService.sendMessage(StreamMessage.defaultMessage());
    }

    /**
     * <h2>使用自定义 发送 Steam 消息</h2>
     * */
    @GetMapping("/send-custom-message")
    public void sendCustomMessage(){
        customSendService.sendMessage(StreamMessage.defaultMessage());
    }




}
