package com.social.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.social.models.Post;
import com.social.models.User;
import com.social.response.ApiResponse;
import com.social.services.PostService;
import com.social.services.UserService;

import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestHeader("Authorization") String jwt)
            throws Exception {

        User reqUser = userService.findUserByJwt(jwt);

        Post createdPost = postService.createNewPost(post, reqUser.getId());
        return new ResponseEntity<Post>(createdPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<ApiResponse> deletedPost(@PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse res = new ApiResponse(message, true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/api/post/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUserPosts(@PathVariable Integer userId) throws Exception {
        List<Post> posts = postService.findPostByUserId(userId);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> getAllPosts() throws Exception {
        List<Post> posts = postService.findAllPost();
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @PutMapping("/api/post/save/{postId}")
    public ResponseEntity<Post> savePostHandler(@PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.savedPost(postId, reqUser.getId());
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PutMapping("/api/post/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.likePost(postId, reqUser.getId());
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

}