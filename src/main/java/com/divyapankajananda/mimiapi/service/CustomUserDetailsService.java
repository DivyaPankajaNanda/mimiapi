package com.divyapankajananda.mimiapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.entity.User;
import com.divyapankajananda.mimiapi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> user = userRepository.findByUsername(username);
        return user.get();
    }

    public Boolean userExists(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent())
            return true;
        else
            return false;
    }

}
