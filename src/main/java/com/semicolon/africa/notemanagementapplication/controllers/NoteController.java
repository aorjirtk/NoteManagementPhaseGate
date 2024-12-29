package com.semicolon.africa.notemanagementapplication.controllers;

import com.semicolon.africa.notemanagementapplication.data.models.Note;
import com.semicolon.africa.notemanagementapplication.dtos.requests.CreateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.UpdateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.responses.ApiResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.CreateNoteResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.UpdateNoteResponse;
import com.semicolon.africa.notemanagementapplication.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/note")
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/createNote")
    public ResponseEntity<?> createNote(@RequestBody CreateNoteRequest createNoteRequest) {
        try{
            CreateNoteResponse createNoteResponse = noteService.createNote(createNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, createNoteResponse), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/update")
    public ResponseEntity<?> updateNote(@RequestBody UpdateNoteRequest updateNoteRequest) {
        try{
            UpdateNoteResponse updateNoteResponse = noteService.update(updateNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, updateNoteResponse), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
