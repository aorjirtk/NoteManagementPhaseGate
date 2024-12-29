package com.semicolon.africa.notemanagementapplication.services;

import com.semicolon.africa.notemanagementapplication.data.models.Note;
import com.semicolon.africa.notemanagementapplication.data.repositories.NoteRepository;
import com.semicolon.africa.notemanagementapplication.dtos.requests.CreateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.requests.UpdateNoteRequest;
import com.semicolon.africa.notemanagementapplication.dtos.responses.CreateNoteResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.DeleteNoteResponse;
import com.semicolon.africa.notemanagementapplication.dtos.responses.UpdateNoteResponse;
import com.semicolon.africa.notemanagementapplication.exception.TitleAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {
    private ModelMapper modelMapper;
    private NoteRepository noteRepository;




    @Override
    public List<Note> getTotalNotes() {
        return noteRepository.findAll();
    }

    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        validateTitle(createNoteRequest.getTitle());
        Note note = modelMapper.map(createNoteRequest, Note.class);
        noteRepository.save(note);

        CreateNoteResponse createNoteResponse = modelMapper.map(note, CreateNoteResponse.class);
        createNoteResponse.setTitle(note.getTitle());
        createNoteResponse.setMessage("Note Created Successfully");
        return createNoteResponse;
    }

    @Override
    public UpdateNoteResponse update(UpdateNoteRequest updateNoteRequest) {
        Note note = findById(updateNoteRequest.getId());
        note.setTitle(updateNoteRequest.getNewTitle());
        note.setContent(updateNoteRequest.getNewContent());
        note.setModifiedDate(updateNoteRequest.getDateUpdated());
        modelMapper.map(updateNoteRequest, Note.class);
        noteRepository.save(note);

        UpdateNoteResponse updateNoteResponse = modelMapper.map(note, UpdateNoteResponse.class);
        updateNoteResponse.setNoteId(note.getId());
        updateNoteResponse.setUpDatedTitle(note.getTitle());
        updateNoteResponse.setUpDatedContent(note.getContent());
        updateNoteResponse.setMessage("Note Updated Successfully");
        return updateNoteResponse;
    }

    @Override
    public DeleteNoteResponse deleteNote(String noteId) {
        Note note = findById(noteId);
        noteRepository.delete(note);
        DeleteNoteResponse deleteNoteResponse = modelMapper.map(note, DeleteNoteResponse.class);
        deleteNoteResponse.setMessage("Note Deleted Successfully");
        return deleteNoteResponse;
    }

    @Override
    public Note findNoteByTitle(String title) {
        return noteRepository.findByTitle(title);
    }


    private Note findById(String id){
        return noteRepository.findById(id)
                .orElseThrow( ()-> new TitleAlreadyExistsException("Note not found"));
    }

    private void validateTitle(String title) {
        boolean existsByTitle = noteRepository.existsByTitle(title);
        if(existsByTitle) throw new TitleAlreadyExistsException("Title already exists");
    }

}
