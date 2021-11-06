package com.isaac.login_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.isaac.login_app.model.User;
import com.isaac.login_app.repository.UserRepository;
import com.isaac.login_app.security.CustomUserDetails;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("Usuario no encontrado, email = " + email);
        }

        return new CustomUserDetails(user.get());
    }
    
}
