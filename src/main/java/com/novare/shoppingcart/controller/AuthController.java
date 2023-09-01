package com.novare.shoppingcart.controller;

import com.novare.shoppingcart.model.User;
import com.novare.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping()
public class AuthController {
    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        User user = userService.signUpUser(email, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists.");
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateOrSignUp(@RequestBody Map<String, String> authData)  {
        String email = authData.get("email");
        String password = authData.get("password");
        User existingUser = userService.getUserByEmail(email);
        Map<String, Object> response = new HashMap<>();
        if (existingUser == null) {
            userService.signUpUser(email,password);
            response.put("success", true);
            response.put("message", "User registered successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            if (bCryptPasswordEncoder.matches(password, existingUser.getHashPassword())) {
                response.put("success", true);
                response.put("message", "Valid credentials");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }
    }
}
