package com.social.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Reels;
import com.social.models.User;
import com.social.repository.ReelsRepository;


@Service
public class ReelsServiceImplementation implements ReelsService {

    @Autowired
    private UserService userService;

    @Autowired
    private ReelsRepository reelsRepository;

    @Override
    public Reels createReel(Reels reel, User user) throws Exception {


        Reels newReel=new Reels();
        newReel.setTitle(reel.getTitle());
        newReel.setUser(user);
        newReel.setVideo(reel.getVideo());

       return reelsRepository.save(newReel);
    }

    @Override
    public List<Reels> findAllReels() throws Exception {
        return reelsRepository.findAll();
    }

    @Override
    public List<Reels> findUsersReel(Integer userId) throws Exception {

        userService.findUserById(userId);
        return reelsRepository.findByUserId(userId);
    }

    
    
}