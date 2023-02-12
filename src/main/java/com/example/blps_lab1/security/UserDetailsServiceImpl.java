package com.example.blps_lab1.security;

import com.example.blps_lab1.model.User;
import com.example.blps_lab1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.
                findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователя с логином " + login + " не существует"));
        return UserDetailsImpl.build(user);
    }
}
