package com.semicolon.africa.notemanagementapplication.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateNoteResponse {
    private String noteId;
    private String message;
    private String upDatedTitle;
    private String upDatedContent;
    private LocalDateTime dateUpdated;
}
