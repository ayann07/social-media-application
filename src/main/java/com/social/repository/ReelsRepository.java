package com.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.models.Reels;
import java.util.List;


public interface ReelsRepository extends JpaRepository<Reels,Integer> {

    public List<Reels> findByUserId(Integer userId);

} 