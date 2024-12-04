package com.social.services;

import java.util.List;

import com.social.models.Chat;
import com.social.models.Message;
import com.social.models.User;

public interface MessageService {

    public Message createMessage(User user,Integer chatId,Message message) throws Exception;

    public List<Message> findChatMessages(Integer chatId) throws Exception;



}
