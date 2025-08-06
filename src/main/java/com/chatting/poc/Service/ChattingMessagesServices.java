package com.chatting.poc.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatting.poc.DataBaseOBJ.ChatMessage;
import com.chatting.poc.JPARepository.ChatMessageRepository;

@Service
public class ChattingMessagesServices {

	@Autowired
	private ChatMessageRepository chattingRepo;
	
	public void saveMessages(ChatMessage chatingMessage) {
		chattingRepo.save(chatingMessage);
	}
}
