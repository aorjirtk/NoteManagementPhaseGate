package com.semicolon.africa.notemanagementapplication.services;

import com.semicolon.africa.notemanagementapplication.data.models.Note;
import com.semicolon.africa.notemanagementapplication.dtos.requests.CreateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.UpdateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.responses.CreateNoteResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.DeleteNoteResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.UpdateNoteResponse;

import java.util.List;

public interface NoteService {

    List<Note> getTotalNotes();

    CreateNoteResponse createNote(CreateNoteRequest createNoteRequest);

    UpdateNoteResponse update(UpdateNoteRequest updateNoteRequest);

    DeleteNoteResponse deleteNote(String noteId);

    Note findNoteByTitle(String title);
}
