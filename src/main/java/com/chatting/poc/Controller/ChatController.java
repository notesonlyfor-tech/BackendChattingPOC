package com.chatting.poc.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatting.poc.DataBaseOBJ.ChatMessage;
import com.chatting.poc.Service.ChattingMessagesServices;

@Controller
public class ChatController {

	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChattingMessagesServices messageService;
    
    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<Map<String, String>> sendMessage(
            @RequestParam("sender") String sender,
            @RequestParam("receiver") String receiver,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image) {

    	logger.info("Sending message from {} to {}", sender, receiver);
    	
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path uploadPath = Paths.get("uploads");

                logger.debug("Message content: {}", content);
                
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                imageUrl = "/uploads/" + fileName;

            } catch (IOException e) {
            	logger.error("Failed to send message", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Image upload failed"));
            }
        }

        // Save message logic here (e.g., to DB)

        Map<String, String> response = new HashMap<>();
        response.put("message", "Message sent");
        response.put("imageUrl", imageUrl);
        
        logger.info("Response message To Frontend : ", response);

        return ResponseEntity.ok(response);
    }

    
    
    

    @MessageMapping("/chat")
    public void sendMessage(ChatMessage message) {
    	
    	logger.info("Send Message Invoked With The Message : ", message);
    	
        // Save message to DB
    	messageService.saveMessages(message);
    	
        // Send to specific user (receiver)
        messagingTemplate.convertAndSendToUser(
            message.getReceiver(), "/queue/messages", message
        );
    }
    
}



