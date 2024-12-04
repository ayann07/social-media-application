package com.social.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Chat;
import com.social.models.Message;
import com.social.models.User;
import com.social.repository.ChatRepository;
import com.social.repository.MessageRepository;

@Service
public class MessageServiceImplementation implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Message createMessage(User user, Integer chatId, Message message) throws Exception {

        Chat chat = chatService.findChatById(chatId);

        Message newmessage = new Message();
        newmessage.setChat(chat);
        newmessage.setContent(message.getContent());
        newmessage.setImage(message.getImage());
        newmessage.setUser(user);
        newmessage.setTimestamp(LocalDateTime.now());

        Message savedMessage=messageRepository.save(newmessage);

        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);
        
        return savedMessage;
    }

    @Override
    public List<Message> findChatMessages(Integer chatId) throws Exception {

        Chat chat = chatService.findChatById(chatId);
        return messageRepository.findByChatId(chatId);
    }

}