package com.example.blps_lab1.service;

import com.example.blps_lab1.config.jwt.JwtUtils;
import com.example.blps_lab1.dto.SignInRequest;
import com.example.blps_lab1.dto.SignUpRequest;
import com.example.blps_lab1.exception.RoleNotFoundException;
import com.example.blps_lab1.exception.UserAlreadyExistException;
import com.example.blps_lab1.model.ERole;
import com.example.blps_lab1.model.Jwt;
import com.example.blps_lab1.model.Role;
import com.example.blps_lab1.model.User;
import com.example.blps_lab1.repository.RoleRepository;
import com.example.blps_lab1.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils, UserRepository userRepository,
                       PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public Jwt authUser(SignInRequest signInRequest) throws BadCredentialsException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        signInRequest.getLogin(),
                        signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return new Jwt(jwt);
    }

    public User saveUser(SignUpRequest signUpRequest) throws UserAlreadyExistException, RoleNotFoundException {
        if (userRepository.findByLogin(signUpRequest.getLogin()).isPresent())
            throw new UserAlreadyExistException("This login is already taken! Try another");

        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent())
            throw new UserAlreadyExistException("This email is already taken! Try another");


        Set<Role> user_roles = new HashSet<>();
        Role userRole = roleRepository
                .findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException("USER role not found!"));
        user_roles.add(userRole);

        User user = new User(signUpRequest.getLogin(), passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail(), user_roles);

        userRepository.save(user);
        return user;
    }

}
