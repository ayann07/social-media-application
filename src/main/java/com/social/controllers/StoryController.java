package com.social.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Story;
import com.social.models.User;
import com.social.services.StoryService;
import com.social.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/story")
    public Story createStory(@RequestBody Story story,@RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwt(jwt);
        Story createdStory=storyService.createStory(story,user);
        
        return createdStory;
    }

    @GetMapping("/api/story/user/{userId}")
    public List<Story> findUserStory(@PathVariable Integer userId) throws Exception{
        
        List<Story> stories=storyService.findStoryByUserId(userId);
        
        return stories;
    }

}