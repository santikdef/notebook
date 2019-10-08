package com.santikdef.notebook.service;

import com.santikdef.notebook.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    void saveNote(Note note);

    void updateText(String text, Long noteId);

    void delete(Long noteId);

    List<Note> getAll();

    List<Note> getByUserId(Long userId);

    List<Note> getAnonymous();

    List<Note> getByCurrentUser();

    Optional<Note> getById(Long id);

}
