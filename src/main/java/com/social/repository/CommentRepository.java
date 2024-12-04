package com.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.models.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

// this CommentRepository interface extends the JpaRepository to interact with the database and inherits several methods from Jpa Repository for performing CRUD operations.
// <Comment, Integer>:
// Comment: The entity that the repository will manage.
// Integer: The type of the entity's primary key (id field).
    
} 