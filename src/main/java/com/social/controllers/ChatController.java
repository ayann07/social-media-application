package com.social.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Chat;
import com.social.models.User;
import com.social.request.CreateChatRequest;
import com.social.services.ChatService;
import com.social.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ChatController {
    
    @Autowired
    private ChatService chatService;


    @Autowired
    private UserService userService;

    @PostMapping("api/chats")
    public Chat createChat(@RequestHeader("Authorization")String jwt ,@RequestBody CreateChatRequest req) throws Exception{
        User reqUser=userService.findUserByJwt(jwt);
        User user2=userService.findUserById(req.getUserId());
        Chat chat=chatService.createChat(reqUser,user2);
        return chat;
    }

    @GetMapping("api/chats")
    public List<Chat> findUsersChat(@RequestHeader("Authorization")String jwt ){

        User user=userService.findUserByJwt(jwt);
        List<Chat> chat=chatService.findUsersChat(user.getId());
        return chat;
    }

}