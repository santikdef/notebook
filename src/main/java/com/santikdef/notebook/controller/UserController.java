package com.santikdef.notebook.controller;

import com.santikdef.notebook.validation.UserValidator;
import com.santikdef.notebook.model.User;
import com.santikdef.notebook.service.SecurityService;
import com.santikdef.notebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private static final String LOGIN = "login";
    private static final String REGISTRATION = "registration";

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private SecurityService securityService;

    @GetMapping(LOGIN)
    public String login() {
        return "login";
    }

    @GetMapping(REGISTRATION)
    public String registration(@ModelAttribute User user) {
        return "registration";
    }

    @PostMapping(REGISTRATION)
    public String registration(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(user);

        //securityService.autoLogin(user.getUsername(), user.getPassword());

        return "redirect:/registration?success";
    }

}
