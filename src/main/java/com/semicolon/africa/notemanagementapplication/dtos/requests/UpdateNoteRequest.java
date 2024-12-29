package com.semicolon.africa.notemanagementapplication.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UpdateNoteRequest {
    private String id;
    private String newTitle;
    private String newContent;
    private LocalDateTime dateUpdated;
}
