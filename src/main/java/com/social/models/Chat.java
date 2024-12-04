package com.social.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//Marks this class as a JPA entity. Indicates that this class is mapped to a database table.
@AllArgsConstructor
//Generates a constructor with parameters for all fields in the class.
@NoArgsConstructor
////Generates a no arguments constructor.
@Getter
@Setter
// generate the getter and setter methods for fields in the class.
public class Chat {

    @Id
    //Marks this field as the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.AUTO)
    //Specifies that the id value will be automatically generated.
    private Integer id;
    //Declares a private field id of type Integer to store the unique identifier of the chat.

    private String chatName, chatImage;

    @ManyToMany
    //Indicates that a chat can have multiple users, and a user can participate in multiple chats.
    //By default, a @ManyToMany relationship will result in a join table being created in database to manage the association between Chat and User.
    private List<User> users = new ArrayList<>();

    private LocalDateTime timestamp;
    // this attribute is used to store the time when the object of this class that is chat being created.

    @OneToMany(mappedBy = "chat")
    //Specifies a one-to-many relationship between Chat and Message entities.Indicates that one chat can have multiple messages. bhot saare message hote h ek chat mai      (R -> L)
    //mappedBy = "chat": Indicates that the chat field in the Message entity owns the relationship.
    private List<Message>messages=new ArrayList<>();

}