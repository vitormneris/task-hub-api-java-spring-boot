package com.codecrafters.taskhubcore.controller;

import com.codecrafters.taskhubcore.controller.dto.ChatInputDTO;
import com.codecrafters.taskhubcore.controller.dto.ChatOutputDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LivechatController {

    @MessageMapping("/new-message")
    @SendTo("/topics/livechat")
    public ChatOutputDTO newMessage(ChatInputDTO chatInput) {
        return new ChatOutputDTO(chatInput.user() + " : " + chatInput.message());
    }
}
