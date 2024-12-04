package com.social.models;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
// mark this class as entity, isse database m saare attributes ko leke springboot table bna dega
@Table(name="users")
// isse table ka naam User(Class name) jo h wo nhi hog aur users hoga jo hmne dia h alag se.
public class User {

    @Id
    //Marks this field as the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.AUTO)
    //Specifies that the id value will be automatically generated.
    private Integer id;
    //Declares a private field id of type Integer to store the unique identifier of the chat.

    private String firstName,lastName,email,password,gender;

    private List<Integer>followers=new ArrayList<Integer>();
    private List<Integer>followings=new ArrayList<Integer>();
    // list to store followers and followings of the user.

    @ManyToMany
    // ye btata h ki bhot saare users bhot saari posts ko save kr skte h aur bhot saari post ko bhot saare users save kr skte h.
    private List<Post>savedPost=new ArrayList<>();

    public User(){
        
    }


    public List<Post> getSavedPost() {
        return savedPost;
    }


    public void setSavedPost(List<Post> savedPost) {
        this.savedPost = savedPost;
    }


    

    public User(int id, String firstName, String lastName, String email, String password, String gender,
            List<Integer> followers, List<Integer> followings, List<Post> savedPost) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.followers = followers;
        this.followings = followings;
        this.savedPost = savedPost;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    public List<Integer> getFollowers() {
        return followers;
    }


    public void setFollowers(List<Integer> followers) {
        this.followers = followers;
    }


    public List<Integer> getFollowings() {
        return followings;
    }


    public void setFollowings(List<Integer> followings) {
        this.followings = followings;
    }

}