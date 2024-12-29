package com.semicolon.africa.notemanagementapplication.services;

import com.semicolon.africa.notemanagementapplication.data.repositories.NoteRepository;
import com.semicolon.africa.notemanagementapplication.dtos.requests.CreateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.UpdateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.responses.CreateNoteResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.DeleteNoteResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.UpdateNoteResponse;
import com.semicolon.africa.notemanagementapplication.exception.TitleAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class NoteServiceTest {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NoteService noteService;
    @BeforeEach
    public void setUp() {
        noteRepository.deleteAll();

    }
    @Test
    public void testThatWeCanCreateNote() {
        CreateNoteResponse createNoteResponse = createNote();
        assertThat(createNoteResponse).isNotNull();
//      assertThat(createNoteResponse.getId()).isNotNull();
        assertThat(createNoteResponse.getMessage()).contains("Note Created Successfully");
        assertThat(noteService.getTotalNotes()).size().isEqualTo(1L);
    }

    private CreateNoteResponse createNote() {
        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("Title");
        createNoteRequest.setContent("Content");
        createNoteRequest.setAuthorEmail("torbem@gmail.com");
        createNoteRequest.setCreationDate(LocalDateTime.now());
        createNoteRequest.setModifiedDate(LocalDateTime.now());
        return noteService.createNote(createNoteRequest);
    }

    @Test
    public void testThatAddingNotesWithSameTitleThrowsException() {
        createNote();
        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("Title");
        createNoteRequest.setContent("Content");
        createNoteRequest.setAuthorEmail("torbem@gmail.com");
        createNoteRequest.setCreationDate(LocalDateTime.now());
        createNoteRequest.setModifiedDate(LocalDateTime.now());
        assertThrows(TitleAlreadyExistsException.class, ()-> noteService.createNote(createNoteRequest));

    }
    @Test
    public void testThatNoteCanBeUpdated(){
        CreateNoteResponse response = createNote();
        UpdateNoteRequest updateNoteRequest = new UpdateNoteRequest();
        updateNoteRequest.setId(response.getId());
        updateNoteRequest.setNewTitle("my new title is Chief");
        updateNoteRequest.setNewContent("New Content");
        UpdateNoteResponse updateNoteResponse = noteService.update(updateNoteRequest);
        assertThat(updateNoteResponse).isNotNull();
        assertThat(updateNoteResponse.getUpDatedTitle()).contains("my new title is Chief");
        assertThat(updateNoteResponse.getNoteId()).isEqualTo(updateNoteRequest.getId());
    }
    @Test
    public void testThatNoteCanBeDeleted(){
        CreateNoteResponse response = createNote();
        String noteId = response.getId();
        DeleteNoteResponse deleteNoteResponse = noteService.deleteNote(noteId);
        assertThat(deleteNoteResponse).isNotNull();
        assertThat(deleteNoteResponse.getMessage()).contains("Note Deleted Successfully");
    }

}