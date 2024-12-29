package com.semicolon.africa.notemanagementapplication.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CreateNoteRequest {
    private String title;
    private String content;
    private String authorEmail;
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
}
