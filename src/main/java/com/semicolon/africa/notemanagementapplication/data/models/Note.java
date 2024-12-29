package com.semicolon.africa.notemanagementapplication.data.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Note {
    private String id;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
}
