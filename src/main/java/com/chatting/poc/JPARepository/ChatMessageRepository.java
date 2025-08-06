package com.chatting.poc.JPARepository;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.chatting.poc.DataBaseOBJ.ChatMessage;

@SpringBootApplication
@EnableJpaRepositories("com.chatting.poc.JPARepository")
@EntityScan("com.chatting.poc.DataBaseOBJ")
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderAndReceiver(String sender, String receiver);
    List<ChatMessage> findByReceiverAndSender(String receiver, String sender);
}
