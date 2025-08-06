package com.chatting.poc.WebSocketConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//	@Autowired
//	private SimpMessagingTemplate messagingTemplate;
//	
//	@Autowired
//	private ChatMessageRepository messageRepo;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("http://localhost:3000") // ✅ match here too
                .withSockJS();
    }

//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setApplicationDestinationPrefixes("/app");      // For client -> server
//        registry.enableSimpleBroker("/topic");                   // For server -> client
//    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }
    
//    @MessageMapping("/chat")
//    public void sendMessage(ChatMessage message) {
//        messageRepo.save(message); // Save to DB
//        messagingTemplate.convertAndSendToUser(
//            message.getReceiver(), "/queue/messages", message
//        );
//    }
}

