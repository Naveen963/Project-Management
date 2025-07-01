package com.projectmanagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecretKey;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);


    public String generateJwtTokenFromUsername(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*60*10))
                .signWith((SecretKey) key())
                .compact();
    }

    public String generateUsernameFromToken(String token){
     return Jwts.parser()
                .verifyWith((SecretKey)key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //Generate signing key
    public Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecretKey)
        );
    }

    public boolean ValidateJwt(String token){
        try{
      Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token) ;
            return !isTokenExpired(token);
        }
        catch (MalformedJwtException exception){
            logger.error("Invalid JWT token: {}", exception.getMessage());
        }
        catch (ExpiredJwtException exception){
            logger.error("JWT token is expired: {}", exception.getMessage());

        }
        catch (UnsupportedJwtException exception){
            logger.error("JWT token is unsupported: {}", exception.getMessage());

        }
        catch (IllegalArgumentException exception){
            logger.error("JWT claims string is empty: {}", exception.getMessage());

        }
        return  false;
    }


    private boolean isTokenExpired(String token){
        return Jwts.parser().verifyWith((SecretKey)key())
                .build().parseSignedClaims(jwtSecretKey)
                .getPayload().getExpiration().before(new Date());
    }

}
