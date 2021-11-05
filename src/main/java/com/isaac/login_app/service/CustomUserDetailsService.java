package com.isaac.login_app.service;

import java.util.Objects;

import com.isaac.login_app.model.User;
import com.isaac.login_app.repository.UserRepository;
import com.isaac.login_app.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("Usuario no encontrado, email = " + email);
        }

        return new CustomUserDetails(user);
    }
    
}
