package com.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.social.models.Post;
import java.util.List;


public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query("select p from Post p where p.user.id=:userId")
    List<Post> findPostByUserId(Integer userId);
    
} 