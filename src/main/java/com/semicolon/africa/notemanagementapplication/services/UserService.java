package com.semicolon.africa.notemanagementapplication.services;

import com.semicolon.africa.notemanagementapplication.data.models.User;
import com.semicolon.africa.notemanagementapplication.dtos.requests.CreateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.LoginUserRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.LogoutRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.RegisterUserRequest;
import com.semicolon.africa.notemanagementapplication.dtos.responses.CreateNoteResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.LoginUserResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.LogoutResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.RegisterUserResponse;

import java.util.Collection;
import java.util.List;

public interface UserService {

    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    List<User> getAllUsers();

    LoginUserResponse login(LoginUserRequest loginUserRequest);

    CreateNoteResponse createNoteWith(CreateNoteRequest request);

    LogoutResponse logout(LogoutRequest logoutRequest);
}
