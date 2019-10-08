package com.santikdef.notebook.service;

import com.santikdef.notebook.exception.NotFoundException;
import com.santikdef.notebook.model.Note;
import com.santikdef.notebook.model.User;
import com.santikdef.notebook.repository.NoteRepository;
import com.santikdef.notebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private static final String ANONYMOUS_USER = "anonymousUser";

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Override
    public void saveNote(Note note) {
        if (note.getIsAnonymous() == null
                || (note.getIsAnonymous() != null && !note.getIsAnonymous())) {
            User user = userService.getCurrentUser();
            if (user != null) {
                note.setUser(user);
            }
        } else {
            note.setIsAnonymous(Boolean.TRUE);
        }
        noteRepository.save(note);
    }


    @Override
    @Transactional
    public void updateText(String text, Long noteId) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note != null) {
            if (isAllowed(note)) {
                noteRepository.updateTextById(text, note.getId());
            } else {
                throw new AccessDeniedException("No permissions for that operation");
            }
        } else {
            throw new NotFoundException("Note not found");
        }
    }

    @Override
    public void delete(Long noteId) {
        Note note = getById(noteId).orElse(null);
        if (note != null) {
            if (isAllowed(note)) {
                noteRepository.delete(note);
            } else {
                throw new AccessDeniedException("No permissions for that operation");
            }
        } else {
            throw new NotFoundException("Note not found");
        }
    }

    @Override
    public List<Note> getAll() {
        return noteRepository.findAll();
    }

    @Override
    public List<Note> getByUserId(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    @Override
    public List<Note> getAnonymous() {
        return noteRepository.findByUserIdIsNull();
    }

    @Override
    public List<Note> getByCurrentUser() {
        String username = securityService.getUsername();
        if (username != null) {
            User user = userRepository.findByUsername(username);
            return getByUserId(user.getId());
        }
        return getAnonymous();
    }

    @Override
    public Optional<Note> getById(Long id) {
        return noteRepository.findById(id);
    }

    private boolean isAllowed(Note note) {
        User user = userService.getCurrentUser();
        if (user != null) {
            return user.getId().equals(note.getUser().getId());
        }
        return false;
    }

}


