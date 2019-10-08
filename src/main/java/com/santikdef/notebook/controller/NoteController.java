package com.santikdef.notebook.controller;

import com.santikdef.notebook.exception.NotFoundException;
import com.santikdef.notebook.model.Note;
import com.santikdef.notebook.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private static final String CREATE = "create";
    private static final String SAVE = "save";
    private static final String DELETE_NOTE = "delete/{noteId}";
    private static final String EDIT_NOTE = "edit/{noteId}";
    private static final String EDIT = "edit";

    @Autowired
    NoteService noteService;

    @GetMapping(CREATE)
    public String create() {
        return "create";
    }

    @PostMapping(SAVE)
    public String save(@ModelAttribute Note note) {
        noteService.saveNote(note);
        return "redirect:/";
    }

    @GetMapping(DELETE_NOTE)
    public String delete(@PathVariable Long noteId) {
        noteService.delete(noteId);
        return "redirect:/";
    }

    @GetMapping(EDIT_NOTE)
    public String edit(@PathVariable Long noteId, Model model) {
        Note note = noteService.getById(noteId).orElseThrow(() -> new NotFoundException("Note not found"));
        model.addAttribute("note", note);
        return "edit";
    }

    @PostMapping(EDIT)
    public String edit(@ModelAttribute Note note) {
        noteService.updateText(note.getText(), note.getId());
        return "redirect:/";
    }
}
