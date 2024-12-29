package com.semicolon.africa.notemanagementapplication.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequest {
    private String email;
    private String password;
}
