package com.social.config;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

    private static SecretKey key=Keys.hmacShaKeyFor(jwtConstant.SECRET_KEY.getBytes());

    // private static SecretKey key: Defines a static secret key used for signing and verifying JWT tokens.
    // Keys.hmacShaKeyFor(jwtConstant.SECRET_KEY.getBytes()): Used to generate a SecretKey suitable for HMAC-SHA algorithms.
    // jwtConstant.SECRET_KEY: A constant that holds your secret key as a String. This should be a secure, random string.
    // .getBytes(): Converts the secret key string into a byte array, as required by the hmacShaKeyFor method.


    public static String generateToken(Authentication auth) {

   // this method generate the jwt token and it takes Authentication object and returns a string representing jwt token.

        @SuppressWarnings("deprecation")
        //Suppresses compiler warnings about deprecated methods used within this method.
        String jwt = Jwts.builder()
        //Initiates the building of a new JWT using the Jwts.builder()
        .setIssuer("ayanraza")
        // set the person who has issued the token
        .setIssuedAt(new Date())
        // this sets the issuedAt time of the token which is current date & time
        .setExpiration(new Date(new Date().getTime() + 86400000))
        // sets the expiry of the generated token 
        .claim("email", auth.getName())
        // Adds a custom claim named "email" to the JWT. auth.getName(): Retrieves the username (or email) from the Authentication object.
        .signWith(key)
        //Signs the JWT using the previously defined secret key. 
        .compact();
        //Finalizes the JWT construction and serializes it into a compact, URL-safe string.
        
        
        return jwt;
        // returns the generated jwt token as a string
    }




    public static String getEmailFromJwtToken(String jwt)
    {

        // this method will extract the email which we have set as a claim in our jwt token.

        jwt=jwt.substring(7);

        // since the jwt token always starts which bearer fefwfwef... , so we want only  fefwfwef... the actual token hence we will do substring operation on token received. 

        @SuppressWarnings("deprecation")
        // this will suppress the compiler from showing deprecated warning
        Claims claims=Jwts.parser()
        // Jwts.parser() will initiate the parsing of token received
        .setSigningKey(key)
        // sets the secret key used to decode the token recieved. If the key is different from the key used to encrypt the claims and generate the token then we will not be able to decrypt the token
        .build()
        //Builds the JWT parser with the specified signing key.
        .parseClaimsJws(jwt)
        //Parses the JWT string, verifying the signature and extracting the claims.
        .getBody();
        //Retrieves the Claims (the JWT payload) from the parsed JWT. ab isme hi ek key hogi email, usko ham ab retreive krlenge.

        String email=String.valueOf(claims.get("email"));
        // Retrieves the value associated with the "email" claim from the JWT. String.valueOf(...): Converts the retrieved claim value to a String. This is useful if the claim might be null or not a String object.

        return email;
        // return the email recieved
    }
}
