package com.santikdef.notebook.controller;

import com.santikdef.notebook.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    NoteService noteService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("notes", noteService.getByCurrentUser());
        return "index";
    }
}
