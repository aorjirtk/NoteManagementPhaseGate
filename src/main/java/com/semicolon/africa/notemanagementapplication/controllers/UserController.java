package com.semicolon.africa.notemanagementapplication.controllers;

import com.semicolon.africa.notemanagementapplication.dtos.requests.CreateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.LoginUserRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.LogoutRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.RegisterUserRequest;
import com.semicolon.africa.notemanagementapplication.dtos.responses.*;
import com.semicolon.africa.notemanagementapplication.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterUserRequest registerUserRequest){
        try {
            RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, registerUserResponse), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginUserRequest loginUserRequest){
        try {
            LoginUserResponse loginUserResponse = userService.login(loginUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, loginUserResponse), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/createNoteWith")
    public ResponseEntity<?> createNoteWith (@RequestBody CreateNoteRequest createNoteRequest){
        try {
            CreateNoteResponse createNoteResponse = userService.createNoteWith(createNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, createNoteResponse), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout (@RequestBody LogoutRequest logoutRequest){
        try {
            LogoutResponse logoutResponse = userService.logout(logoutRequest);
            return new ResponseEntity<>(new ApiResponse(true, logoutResponse), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
