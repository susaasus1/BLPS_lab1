package com.example.blps_lab1.controller;

import com.example.blps_lab1.dto.JwtResponse;
import com.example.blps_lab1.dto.SignInRequest;
import com.example.blps_lab1.dto.SignUpRequest;
import com.example.blps_lab1.dto.SuccessResponse;
import com.example.blps_lab1.exception.RoleNotFoundException;
import com.example.blps_lab1.exception.UserAlreadyExistException;
import com.example.blps_lab1.model.Jwt;
import com.example.blps_lab1.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cook/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign_in")
    public ResponseEntity<?> authUser(@RequestBody SignInRequest signInRequest) throws BadCredentialsException {


        Jwt jwt = authService.authUser(signInRequest.getLogin(),
                signInRequest.getPassword());

        return ResponseEntity.ok(new JwtResponse(jwt.getToken()));


    }

    @PostMapping("/sign_up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) throws UserAlreadyExistException, RoleNotFoundException {


        authService.saveUser(signUpRequest.getLogin(), signUpRequest.getPassword(),
                signUpRequest.getEmail());

        return ResponseEntity.ok(new SuccessResponse("User registered successfully!"));


    }


}
