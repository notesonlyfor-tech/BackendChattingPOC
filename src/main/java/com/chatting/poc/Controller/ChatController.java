package com.chatting.poc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.chatting.poc.DataBaseOBJ.ChatMessage;
import com.chatting.poc.Service.ChattingMessagesServices;

@Controller
public class ChatController {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChattingMessagesServices messageService;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessage message) {
        // Save message to DB
    	messageService.saveMessages(message);
    	
        // Send to specific user (receiver)
        messagingTemplate.convertAndSendToUser(
            message.getReceiver(), "/queue/messages", message
        );
    }
    
}



