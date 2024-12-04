package com.social.services;

import java.util.List;

import com.social.models.Reels;
import com.social.models.User;

public interface ReelsService {

    public Reels createReel(Reels reel, User user) throws Exception;

    public List<Reels> findAllReels() throws Exception;

    public List<Reels> findUsersReel(Integer userId) throws Exception;

}