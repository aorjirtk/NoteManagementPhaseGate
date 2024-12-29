package com.semicolon.africa.notemanagementapplication.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserResponse {
    private String id;
    private String email;
    private String message;

}
