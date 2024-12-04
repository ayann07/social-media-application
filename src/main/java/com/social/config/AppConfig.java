package com.social.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
// @Configuration - It tells Spring that this class contains configurations that should be processed during runtime.
@EnableWebSecurity
// @EnableWebSecurity - This annotation allows you to configure web-based security for specific HTTP requests.
public class AppConfig {

    @Bean
    // this SecurityFilterChain method takes an HttpSecurity object as a parameter which allows you to configure web based security for http requests.
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(Authorize -> Authorize
        // The authorizeHttpRequests method accepts a lambda expression to specify access restrictions.
        .requestMatchers("/api/**").authenticated()
        // requestMatchers - Specifies that any HTTP request matching the pattern /api/** must be authenticated. Users must log in or provide valid credentials to access these endpoints.
        .anyRequest().permitAll())
        // anyRequest().permitAll() - Allows all other requests that do not match/api/** to be accessed without authentication. This makes the rest of your application publicly accessible.
        .addFilterBefore(new jwtValidator(), BasicAuthenticationFilter.class)
        // addFilterBefore() - Adds a custom filter called jwtValidator before the BasicAuthenticationFilter in the security filter chain. This custom filter will process incoming requests to validate JWT tokens before standard authentication occurs.
        .csrf(csrf -> csrf.disable())
        // csrf() - Disables Cross-Site Request Forgery (CSRF) protection. This is common in stateless REST APIs where CSRF protection is not necessary because the server does not maintain user session state.
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // sessionManagement() - Configures the session management to be stateless. By setting the SessionCreationPolicy to STATELESS, the application will not create or use HTTP sessions to store security context information.

        return http.build();
        // Builds the HttpSecurity configuration and returns a SecurityFilterChain object. This object is used by Spring Security to apply the defined security settings.
    }

    @Bean
    // Declares a method passwordEncoder that returns a PasswordEncoder object. This bean will be used for encoding passwords in your application.
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}