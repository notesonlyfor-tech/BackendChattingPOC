package com.chatting.poc.Controller;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	

	private static final Logger logger = LoggerFactory.getLogger(ChatHistoryController.class);


    @Autowired
    private ChatMessageRepository messageRepo;
    
    @GetMapping("/history/{sender}/{receiver}")
    public List<ChatMessage> getHistory(@PathVariable String sender, @PathVariable String receiver) {
    	logger.info("Reading Chatting Histry Sender: {} and Reciver: {} : ", sender,receiver);
    	List<ChatMessage> messages = messageRepo.findBySenderAndReceiver(sender, receiver);
        messages.addAll(messageRepo.findByReceiverAndSender(sender, receiver));
        messages.sort(Comparator.comparing(ChatMessage::getTimestamp));
        logger.info("Entire Histry From Above Sender and Reciver: ", messages);
        return messages;
    }
}

