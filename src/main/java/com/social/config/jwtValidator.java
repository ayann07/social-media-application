package com.social.config;

import java.io.IOException;
import java.util.*;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class jwtValidator extends OncePerRequestFilter {

    //The class extends OncePerRequestFilter, which is a Spring Security filter base class that guarantees a single execution per request dispatch. This is important for filters that need to be applied only once per request.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

                //The core method of OncePerRequestFilter that needs to be implemented. It performs the actual filtering work.

        String jwt = request.getHeader(jwtConstant.JWT_HEADER);

        // retreives the jwt token i.e bearer dkwefeer...

        if (jwt != null) {
            // it means jwt is not null and contains something (token)
            try {
                String email = JwtProvider.getEmailFromJwtToken(jwt);
                // retrieves email from jwt
                List<GrantedAuthority> authorities = new ArrayList<>();
                //Initializes an empty list of GrantedAuthority objects. GrantedAuthority: Represents an authority granted to the user (e.g., roles or permissions).In this case, the list is empty, meaning no specific authorities are assigned.

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

            //Creates an Authentication object using UsernamePasswordAuthenticationToken.
            // email: Used as the principal (usually the username or email of the user).
            // null: The credentials are set to null since we are not using password authentication in this context.
            // authorities: The list of granted authorities (empty in this case).

            //This token represents an authenticated user in the Spring Security context.

                SecurityContextHolder.getContext().setAuthentication(authentication);
            //Retrieves the current security context and sets the Authentication object created earlier. This effectively authenticates the user for the current request within the application.

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token");
                //Throws a BadCredentialsException, indicating that the authentication credentials are invalid.

            }
        }
        filterChain.doFilter(request, response);
        //Passes the request and response to the next filter in the chain. This ensures that processing continues and other filters or servlets can handle the request as needed.
    }
}
