package com.chandradip.weatherforecastapi.service;

import com.chandradip.weatherforecastapi.collections.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    public String generateToken(User userDetails);

    public boolean isTokenValid(String token, User user);

    public String getUserNameFromToken(String token);
}
