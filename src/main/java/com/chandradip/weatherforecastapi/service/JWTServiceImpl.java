package com.chandradip.weatherforecastapi.service;

import com.chandradip.weatherforecastapi.collections.User;
import com.chandradip.weatherforecastapi.exception.InvalidOrExpiredTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;
@Service
@Slf4j
public class JWTServiceImpl implements JWTService {

    @Value("${app.security.jwt.secret.key}")
    private String secretKey;

    @Override
    public String generateToken(User user) {
        try {
            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                    .signWith(getSigKey())
                    .compact();
        } catch (SignatureException e) {
            // Log the error
            log.error("Exception occurred in tokenGeneration : {}", e.getMessage());
            // Handle the exception, return null or throw custom exception
            return null;
        }
    }

//    @Override
//    public boolean isTokenValid(String token, User userDetails) {
//        try {
//            return userDetails.getUsername().equals(getUserNameFromToken(token))
//                    && !isTokenExpired(token);
//        } catch (SignatureException e) {
//            // Log the error
//            log.error("Exception isValidToken : {}", e.getMessage());
//            // Handle the exception, return false or throw custom exception
//            return false;
//        }
//    }


    @Override
    public boolean isTokenValid(String token, User user) {
        return user.getUsername().equals(getUserNameFromToken(token))
                && !isTokenExpired(token);
    }

//    @Override
//    public String getUserNameFromToken(String token) {
//        try {
//            return extractClaim(token, Claims::getSubject);
//        } catch (SignatureException e) {
//            // Log the
//            log.error("Exception in getUserNameFromToken : {}", e.getMessage());
//            // Handle the exception, return null or throw custom exception
//            return null;
//        }
//    }
    public String getUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Other methods...

    private Key getSigKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigKey()).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(Date.from(Instant.now()));
    }
}
