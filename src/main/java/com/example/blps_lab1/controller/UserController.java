package com.example.blps_lab1.controller;

import com.example.blps_lab1.dto.SignInRequest;
import com.example.blps_lab1.dto.SignUpRequest;
import com.example.blps_lab1.dto.SuccessResponse;
import com.example.blps_lab1.exception.RoleNotFoundException;
import com.example.blps_lab1.exception.UserAlreadyExistException;
import com.example.blps_lab1.model.Jwt;
import com.example.blps_lab1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cook/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign_in")
    public ResponseEntity<?> authUser(@RequestBody SignInRequest signInRequest) throws BadCredentialsException {


        Jwt jwt = userService.authUser(signInRequest);

        return ResponseEntity.ok(new SuccessResponse(jwt.getToken()));


    }

    @PostMapping("/sign_up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) throws UserAlreadyExistException, RoleNotFoundException {


        userService.saveUser(signUpRequest);

        return ResponseEntity.ok(new SuccessResponse("Пользователь успешно зарегистрирован!"));


    }


}
