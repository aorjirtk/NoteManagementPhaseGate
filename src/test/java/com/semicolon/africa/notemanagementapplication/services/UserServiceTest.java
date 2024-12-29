package com.semicolon.africa.notemanagementapplication.services;

import com.semicolon.africa.notemanagementapplication.data.models.User;
import com.semicolon.africa.notemanagementapplication.data.repositories.UserRepository;
import com.semicolon.africa.notemanagementapplication.dtos.requests.CreateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.LoginUserRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.LogoutRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.RegisterUserRequest;
import com.semicolon.africa.notemanagementapplication.dtos.responses.CreateNoteResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.LoginUserResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.LogoutResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.RegisterUserResponse;
import com.semicolon.africa.notemanagementapplication.exception.EmailExistsException;
import com.semicolon.africa.notemanagementapplication.exception.IncorrectDetailsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }
    @Test
    public void registerUserTest() {
        RegisterUserResponse registerUserResponse = userRegister();
        assertThat(registerUserResponse).isNotNull();
        assertThat(registerUserResponse.getMessage()).contains("User registered successfully");
        assertThat(registerUserResponse.getEmail()).isEqualTo("torbem@gmail.com");
        assertThat(userService.getAllUsers().size()).isEqualTo(1L);
    }

    private RegisterUserResponse userRegister() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Torbem");
        registerUserRequest.setLastName("Aorjir");
        registerUserRequest.setEmail("torbem@gmail.com");
        registerUserRequest.setPassword("123456");
        return userService.register(registerUserRequest);
    }
    @Test
    public void testThatUserCannotRegisterTwiceWithSameEmail() {
        userRegister();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstName("Torbem");
        registerUserRequest.setLastName("Aorjir");
        registerUserRequest.setEmail("torbem@gmail.com");
        registerUserRequest.setPassword("123456");
        assertThrows(EmailExistsException.class, ()->userService.register(registerUserRequest));
    }
    @Test
    public void testThatRegisteredUserCanLogin(){
        LoginUserResponse loginUserResponse = userLogin();
        assertThat(loginUserResponse).isNotNull();
        assertThat(loginUserResponse.isLoggedIn()).isEqualTo(true);
    }

    private LoginUserResponse userLogin() {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setPassword("123456");
        loginUserRequest.setEmail("torbem@gmail.com");
        return userService.login(loginUserRequest);
    }
    @Test
    public void testThatUserCannotLoginWithWrongPassword() {
        userRegister();
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setPassword("wrongPassword");
        loginUserRequest.setEmail("torbem@gmail.com");
        assertThrows(IncorrectDetailsException.class, ()->userService.login(loginUserRequest));
    }

    @Test
    public void testThatUserCanCreateNoteSuccessfully() {
        RegisterUserResponse registerUserResponse = userRegister();
        LoginUserResponse loginUserResponse = userLogin();
        CreateNoteRequest request = new CreateNoteRequest();
        request.setTitle("Torbem");
        request.setAuthorEmail("torbem@gmail.com");
        request.setContent("This is a note");
        request.setModifiedDate(LocalDateTime.now());
        request.setCreationDate(LocalDateTime.now());
        CreateNoteResponse createNoteResponse = userService.createNoteWith(request);
        assertThat(createNoteResponse.getId()).isNotNull();
        User user = userRepository.findByEmail(loginUserResponse.getEmail()).orElseThrow();
        assertThat(user.getListOfNotes().size()).isEqualTo(1);
    }
    @Test
    public void testThatUserCanLogOutSuccessfully() {
        userRegister();
        userLogin();
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setEmail("torbem@gmail.com");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);
        assertThat(logoutResponse).isNotNull();
        assertThat(logoutResponse.getMessage()).contains("Logged out successfully");
    }


}