package com.semicolon.africa.notemanagementapplication.dtos.responses;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateNoteResponse {
    private String message;
    private String id;
    private String title;

}
