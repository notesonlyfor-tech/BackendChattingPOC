package com.chatting.poc.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatting.poc.DataBaseOBJ.ChatMessage;
import com.chatting.poc.JPARepository.ChatMessageRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/chat")
public class ChatHistoryController {

    @Autowired
    private ChatMessageRepository messageRepo;

    // Fetch messages between two users
    @GetMapping("/history/{sender}/{receiver}")
    public List<ChatMessage> getChatHistory(@PathVariable String sender, @PathVariable String receiver) {
        List<ChatMessage> sentMessages = messageRepo.findBySenderAndReceiver(sender, receiver);
        List<ChatMessage> receivedMessages = messageRepo.findByReceiverAndSender(sender, receiver);

        List<ChatMessage> fullChat = new ArrayList<>();
        fullChat.addAll(sentMessages);
        fullChat.addAll(receivedMessages);

        fullChat.sort(Comparator.comparing(ChatMessage::getTimestamp)); // assuming timestamp field exists
        return fullChat;
    }
}

