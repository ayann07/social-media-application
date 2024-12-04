package com.social.services;

import java.util.List;

import com.social.models.Story;
import com.social.models.User;

public interface StoryService {

    public Story createStory(Story story, User user) throws Exception;
    
    public List<Story> findStoryByUserId(Integer userId) throws Exception;
}