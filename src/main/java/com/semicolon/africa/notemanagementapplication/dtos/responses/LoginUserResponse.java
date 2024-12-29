package com.semicolon.africa.notemanagementapplication.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserResponse {
    private String message;
    private String email;
    private boolean isLoggedIn;
}
