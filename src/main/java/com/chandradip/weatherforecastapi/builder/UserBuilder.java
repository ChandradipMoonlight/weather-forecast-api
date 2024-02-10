package com.chandradip.weatherforecastapi.builder;

import com.chandradip.weatherforecastapi.collections.User;
import com.chandradip.weatherforecastapi.enums.UserRoles;
import com.chandradip.weatherforecastapi.mappers.UserRequest;
import com.chandradip.weatherforecastapi.mappers.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User buildUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .middleName(userRequest.getMiddleName())
                .lastName(userRequest.getLastName())
                .isVerified(false)
                .role(UserRoles.USER.name())
                .mobileNumber(userRequest.getMobileNumber())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
    }

    public UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .isVerified(user.isVerified())
                .mobileNumber(user.getMobileNumber())
                .build();
    }
}
