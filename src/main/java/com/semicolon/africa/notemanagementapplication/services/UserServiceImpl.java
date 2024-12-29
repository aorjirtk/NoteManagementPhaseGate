package com.semicolon.africa.notemanagementapplication.services;

import com.semicolon.africa.notemanagementapplication.configurations.Mapper;
import com.semicolon.africa.notemanagementapplication.data.models.Note;
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
import com.semicolon.africa.notemanagementapplication.exception.UserLoginException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    private NoteService noteService;
    

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        validateExistingEmail(registerUserRequest.getEmail());
        registerUserRequest.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        User user = modelMapper.map(registerUserRequest, User.class);
        userRepository.save(user);
        RegisterUserResponse registerUserResponse = modelMapper.map(user, RegisterUserResponse.class);
        registerUserResponse.setMessage("User registered successfully");
        registerUserResponse.setEmail(registerUserRequest.getEmail());
        registerUserResponse.setEmail(user.getEmail());
        registerUserResponse.setId(user.getId());
        return registerUserResponse;

    }
    private void validateExistingEmail(String email) {
        boolean existsByEmail = userRepository.existsByEmail(email);
        if (existsByEmail) {
            throw new EmailExistsException(email  + "already exists");
        }
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public LoginUserResponse login(LoginUserRequest loginUserRequest) {
        User user = findUserByEmail(loginUserRequest.getEmail());
        validatePassword(user, loginUserRequest.getPassword());
        user.setLoggedIn(true);
        userRepository.save(user);

        LoginUserResponse loginUserResponse = modelMapper.map(user, LoginUserResponse.class);
        loginUserResponse.setMessage("login successful");
        loginUserResponse.setEmail(user.getEmail());


        return loginUserResponse;
    }

    @Override
    public CreateNoteResponse createNoteWith(CreateNoteRequest request) {
        User user = findUserByEmail(request.getAuthorEmail());
        validateUserLogin(user);
        CreateNoteResponse createNoteResponse = noteService.createNote(request);
        Note note = noteService.findNoteByTitle(createNoteResponse.getTitle());
        List<Note> notes = user.getListOfNotes();
        notes.add(note);
        user.setListOfNotes(notes);
        userRepository.save(user);
        return createNoteResponse;
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        User user = findUserByEmail(logoutRequest.getEmail());
        user.setLoggedIn(false);
        userRepository.save(user);
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setMessage("Logged out successfully");
        return logoutResponse;
    }

    private void validateUserLogin(User user) {
        if(!user.isLoggedIn()) throw new UserLoginException("You must be Logged in");
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new EmailExistsException("user not found"));
    }
    private void validatePassword(User user, String password) {
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new EmailExistsException("invalid details");
        }
    }
}
