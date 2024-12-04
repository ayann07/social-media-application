package com.social.controllers;

import java.util.*;

import com.social.models.User;
import com.social.repository.UserRepository;
import com.social.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/api/users")
    public List<User> getUsers() {


        // this method returns the list of all users present in our database.

        List<User> users = userRepository.findAll();
        // we can simply call the findALl() method which we get in userRepository after extending it from JpaRepository

        return users;
        // now simply return the list of users retrived.
    }

    @GetMapping("/api/users/{userId}")
    public User getUserById(@PathVariable("userId") Integer id) throws Exception {

        // @PathVariable() - dekho ye annotation ham isliye use krte jab hame url se koi variable wali value nikalni hoti h tb ye use krte h

        User user = userService.findUserById(id);
        // ye findUserById() is already present in jpaRepository. toh wo use krke User nikaal lenge.

        return user;
        // return the user which we have retreived.

    }

    @PutMapping("/api/users")
    //This annotation maps HTTP PUT requests to the /api/users endpoint to the updateUser method.
    public User updateUser(@RequestBody User user, @RequestHeader("Authorization") String jwt) throws Exception {

    // @RequestBody User user:
    // Binds the HTTP request body to a User object.
    // The incoming JSON payload is deserialized into a User instance.
    // Purpose: Contains the updated data for the user.

    // @RequestHeader("Authorization") String jwt:
    // Binds the Authorization HTTP header to the jwt parameter.
    // Purpose: Retrieves the JWT (JSON Web Token) from the request header for authentication.

        User reqUser = userService.findUserByJwt(jwt);
        // finds user by jwt, userService m ek method bna dia h findUserByJwt() wo call krke user nikal lenge jo logged in h.

        User updatedUser = userService.updateUser(user, reqUser.getId());
        // now we will update the user , we will pass 2 parameters the updated user stored in user object which we get from @RequestBody and the id of the logged in user.

        return updatedUser;
        // now return the updatedUser.
    }

    @PutMapping("/api/users/follow/{userId2}")
    public User followUserHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer userId2)
    throws Exception {

        // @RequestHeader - isse ham jwt ka token nikalte
        // @PathVariable - isse ham url mai variable ko access krte

        User reqUser = userService.findUserByJwt(jwt);
        // find logged in user.

        User user = userService.followUser(reqUser.getId(), userId2);
        // now logged in user ki id aur dusre user ki id jisko follow krna wo bhej denge ham userService ke followUser() method mai.

        return user; 
    }


    @GetMapping("/users/search")
    public List<User> searchUser(@RequestParam("query") String query) {

        // @RequestParam() se ham ?query= ese jab likhenege toh wo value ham access krlete h 

        List<User> users = userService.searchUser(query);
        // userService ke andar searchUser() method h jisme query pass krke ham list of users nikal skte h depending on the seach query.
        return users;
        // return krdia ab list of users ko as a json response.
    }

    @GetMapping("/api/users/profile")
    public User getUserFromToken(@RequestHeader("Authorization") String jwt) {


        // iss method se ham user nikal lete jwt token se

        User user = userService.findUserByJwt(jwt);
        // userService k andar findUserByJWt() method m jwt token bhej kr user jo logged in wo nikal lenge.

        user.setPassword(null);
        // ham nhi chahte ki password field jaye as a response frontend pr islie setPassword se usko null krdia
        return user;
        // return krdia user ko phir
    }
}