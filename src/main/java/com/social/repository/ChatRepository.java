package com.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.social.models.Chat;
import com.social.models.User;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

// public interface ChatRepository:
// Declares a public interface named ChatRepository. This interface will be used as a repository for Chat entities.
// extends JpaRepository<Chat, Integer>: Indicates that ChatRepository extends the JpaRepository interface provided by Spring Data JPA.
// <Chat, Integer>:
// Chat: The entity that the repository will manage.
// Integer: The type of the entity's primary key (id field).
// Note - By extending JpaRepository, ChatRepository inherits several methods for performing CRUD operations and pagination.

    public List<Chat> findByUsersId(Integer userId);
    
    // Derived Query: The framework interprets this method name and constructs a query that finds all Chat entities where the users collection contains a User with the specified id.
    // How It Works:
    //findBy: Indicates a query method.
    // UsersId: Navigates through the users collection in the Chat entity and targets the id field of the User entity.
    

    @Query("select c from Chat c Where :user Member of c.users And :reqUser Member of c.users")
// Specifies a custom JPQL (Java Persistence Query Language) query for the method.
// This query selects a Chat entity where both specified users are members of the chat.
// :user and :reqUser are named parameters in the query.
// Member of c.users: Checks if the user is a member of the users collection in the Chat entity.
    public Chat findChatByUsersId(@Param("user") User user, @Param("reqUser") User reqUser);
// Chat: The method returns a single Chat entity.
// findChatByUsersId: The method name.
// @Param("user") User user: The first user parameter, mapped to the :user parameter in the query.
// @Param("reqUser") User reqUser: The second user parameter, mapped to the :reqUser parameter in the query.
// Purpose:
// This method finds a Chat where both the specified users are participants.
}