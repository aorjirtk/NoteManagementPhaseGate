package com.semicolon.africa.notemanagementapplication.data.repositories;

import com.semicolon.africa.notemanagementapplication.data.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String> {
    boolean existsByTitle(String title);

    Note findByTitle(String title);

}
