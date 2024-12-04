package com.social.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Story;
import com.social.models.User;
import com.social.repository.StoryRepository;

@Service
public class StoryServiceImplementation implements StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService;

    @Override
    public Story createStory(Story story, User user) throws Exception {

        Story createdStory=new Story();
        createdStory.setCaptions(story.getCaptions());
        createdStory.setUser(user);
        createdStory.setImage(story.getImage());
        createdStory.setTimestamp(LocalDateTime.now());

       return storyRepository.save(createdStory);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws Exception {

        User user=userService.findUserById(userId);
        return storyRepository.findByUserId(user.getId());
    }
    
}