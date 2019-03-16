package com.example.demo.security.rest;

import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.model.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.config.model.TokenUserDetails;
import com.example.demo.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );

        TokenUserDetails principal = (TokenUserDetails) authentication.getPrincipal();
        String token = principal.getToken();
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @GetMapping("/register/checkEmail/{email}")
    public ResponseEntity checkEmailAddress(@PathVariable("email") String email) {
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
