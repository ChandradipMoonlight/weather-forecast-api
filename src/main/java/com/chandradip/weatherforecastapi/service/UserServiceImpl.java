package com.chandradip.weatherforecastapi.service;

import com.chandradip.weatherforecastapi.builder.UserBuilder;
import com.chandradip.weatherforecastapi.collections.User;
import com.chandradip.weatherforecastapi.constants.AppConstants;
import com.chandradip.weatherforecastapi.exception.ResourceNotFoundException;
import com.chandradip.weatherforecastapi.exception.UserAlreadyPresent;
import com.chandradip.weatherforecastapi.mappers.AppResponse;
import com.chandradip.weatherforecastapi.mappers.UserLogin;
import com.chandradip.weatherforecastapi.mappers.UserRequest;
import com.chandradip.weatherforecastapi.mappers.UserResponse;
import com.chandradip.weatherforecastapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserBuilder userBuilder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Override
//    public Mono<UserResponse> registerUser(UserRequest userRequest) {
//        userRepository.findByEmailId(userRequest.getEmail())
//                .doOnSuccess(user -> Mono.error(new UserAlreadyPresent("User Already Present with given EmailId : "+user.getEmail(), HttpStatus.CONFLICT.value())))
//                .subscribe();
//        return userRepository.save(userBuilder.buildUser(userRequest))
//                .map(userBuilder::buildUserResponse);
//    }
    @Override
    public Mono<AppResponse> registerUser(UserRequest userRequest) {
        return userRepository.findByEmail(userRequest.getEmail())
                .flatMap(user -> Mono.error(new UserAlreadyPresent("User Already Present with given EmailId : " + user.getEmail(), HttpStatus.CONFLICT.value())))
                .switchIfEmpty(
                        userRepository.save(userBuilder.buildUser(userRequest))
                                .map(userBuilder::buildUserResponse)
                )
                .map(userResponse -> new AppResponse(userResponse, AppConstants.SUCCESS));
    }



    @Override
    public Mono<AppResponse> verifyUser(String token) {
        return null;
    }

    @Override
    public Mono<AppResponse> loginUser(UserLogin userLogin) {
        return this.loadUserByUsername(userLogin.getEmail())
                .filter(user -> {
                    return passwordEncoder.matches(userLogin.getPassword(), user.getPassword());
                })
                .map(user -> new AppResponse(jwtService.generateToken(user), AppConstants.SUCCESS))
                .switchIfEmpty(Mono.just(new AppResponse("Invalid password", AppConstants.FAIL, HttpStatus.UNAUTHORIZED.value())));
    }

    @Override
    public Mono<User> loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(()-> new ResourceNotFoundException("User", "Email", email)));
    }

}
