package com.novare.shoppingcart.service;

import com.novare.shoppingcart.model.User;
import com.novare.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// UserService.java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User signUpUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setHashPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getHashPassword())) {
            return user;
        }
        return null;
    }
}