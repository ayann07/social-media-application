package com.social.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.config.JwtProvider;
import com.social.models.User;
import com.social.repository.UserRepository;
import com.social.request.LoginRequest;
import com.social.response.AuthResponse;
import com.social.services.CustomUserDetailsService;

@RestController
//This annotation marks the class as a RESTful controller, meaning it handles HTTP requests and returns responses in a RESTful manner. It combines @Controller and @ResponseBody, so each method in this class will return a response body directly.
@RequestMapping("/auth")
//Specifies that all the request mappings in this controller will be prefixed with /auth. For example, /signup becomes /auth/signup.
public class AuthController {


    @Autowired
    //Injects the UserRepository bean into this class. UserRepository is an interface that extends JpaRepository or a similar Spring Data repository interface for database operations on User entities.
    private UserRepository userRepository;
    //Declares a private member variable to hold the injected UserRepository.

    @Autowired
    //Injects the PasswordEncoder bean. This is used to encode (hash) passwords before storing them.
    private PasswordEncoder passwordEncoder;
    //Declares a private member variable for the password encoder.

    @Autowired
    //Injects your custom implementation of UserDetailsService, which is used to load user-specific data during authentication.
    private CustomUserDetailsService customUserDetailsService;
    //Declares a private member variable for the service.


    @PostMapping("/signup")
    // this will map the method with post mapping with the given url.
    public AuthResponse createUser(@RequestBody User user) throws Exception {

    // Indicates that the method parameter user should be bound to the body of the HTTP request. The incoming JSON is deserialized into a User object.

        User isExist = userRepository.findByEmail(user.getEmail());
    // Checks if a user with the provided email already exists in the database userRepository.findByEmail(user.getEmail()): Calls a custom method in UserRepository to find a user by email.


        if (isExist != null) {
            throw new Exception("email already used with another account");
            
            // Checks if isExist is not null, meaning a user with that email already exists.Throws a generic Exception with the message "email already used with another account".
        }

        User newUser = new User();
        // Creates a new instance of the User entity to populate and save to the database.
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setGender(user.getGender());
        // set the user details


        User savedUser = userRepository.save(newUser);
        //Saves the new user to the database using userRepository.

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        // Creates an Authentication object using the user's email and password.
        // UsernamePasswordAuthenticationToken: A simple implementation of Authentication for username and password authentication.
        // Note: Here, savedUser.getPassword() is the hashed password.

        String token = JwtProvider.generateToken(authentication);
        // now we will generate the token for the signed in user.

        AuthResponse res = new AuthResponse(token, "Register success");
        // we have created a custom class AuthResponse for creating the response, it contains token generated and message.

        return res;
        // return the res object of AuthResponse class containing the response.
    }

    @PostMapping("/signin")
    // this will map the method with the post mapping with the given url.
    public AuthResponse signin(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        //Calls a private method authenticate to authenticate the user using the provided email and password and stores the resulting Authentication object.

        String token = JwtProvider.generateToken(authentication);
        //Generates a JWT token for the authenticated user.

        AuthResponse res = new AuthResponse(token, "Login success");
        //Creates an AuthResponse object containing the JWT token and a success message.

        return res;
        // return AuthResponse object res.

    }

    private Authentication authenticate(String email, String password) {


        // Declares a private helper method authenticate that takes an email and password.Returns an Authentication object upon successful authentication.

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        // Uses the customUserDetailsService to load user details by email. UserDetails: An interface that provides core user information.
        
        if (userDetails == null)
        // Checks if userDetails is null (user not found).
        throw new BadCredentialsException("invalid username");

        
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            //Uses passwordEncoder to compare the raw password with the encoded password stored in userDetails.
            throw new BadCredentialsException("invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // Creates and returns an Authentication object upon successful authentication.
        // userDetails: Set as the principal.
        // null: No credentials are passed along.
        // userDetails.getAuthorities(): Includes any granted authorities (roles, permissions).
    }
}