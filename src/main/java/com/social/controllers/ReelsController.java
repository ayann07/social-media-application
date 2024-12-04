package com.social.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Reels;
import com.social.models.User;
import com.social.services.ReelsService;
import com.social.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReelsController {

    @Autowired
    private ReelsService reelsService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/reels")
    public Reels createReels(@RequestBody Reels reel, @RequestHeader("Authorization") String jwt) throws Exception {

        User reqUser = userService.findUserByJwt(jwt);

        Reels createdReel = reelsService.createReel(reel, reqUser);
        return createdReel;
    }

    @GetMapping("/api/reels")
    public List<Reels> findAllReels() throws Exception {

        List<Reels> reels = reelsService.findAllReels();
        return reels;
    }

    @GetMapping("/api/reels/user/{userId}")
    public List<Reels> findUserReels(@PathVariable Integer userId) throws Exception {

        List<Reels> reels = reelsService.findUsersReel(userId);
        return reels;
    }

}