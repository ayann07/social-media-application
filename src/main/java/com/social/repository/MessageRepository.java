package com.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.models.Message;

public interface MessageRepository extends JpaRepository<Message,Integer>{

   // By extending JpaRepository, MessageRepository inherits several methods for performing standard database operations like save(), findAll(), findById(), delete(), etc.

    public List<Message> findByChatId(Integer chatId);

// Declares a custom query method to find messages associated with a specific chat.

// findByChatId(Integer chatId):
// Method Name Interpretation:
// findBy: Indicates that this method will perform a query to retrieve data.
// ChatId: Refers to the chat property in the Message entity and navigates to the id property of the Chat entity.
// Integer chatId:
// The parameter represents the id of the Chat whose messages you want to retrieve.

// How Spring Data JPA Processes This Method:
// Spring Data JPA analyzes the method name and creates a query that finds all Message entities where the chat's id matches the provided chatId.
// It effectively translates to:
// SELECT * FROM messages WHERE chat_id = :chatId
    
} 