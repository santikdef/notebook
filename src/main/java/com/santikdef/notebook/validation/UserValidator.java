package com.santikdef.notebook.validation;

import com.santikdef.notebook.model.User;
import com.santikdef.notebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private static final String USERNAME_PATTERN = "[a-zA-Z][a-zA-Z0-9_.-]{2,19}";

    private static final String PASSWORD_PATTERN = "(?=^.{8,32}$)(?=.*[A-Z])(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d].*";

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required");
        if (userService.getByUsername(user.getUsername()) != null) {
            errors.rejectValue("username","username.exists");
        } else {
            if (!user.getUsername().matches(USERNAME_PATTERN)) {
                errors.rejectValue("username","username.incorrect");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
        if (!user.getPassword().matches(PASSWORD_PATTERN)) {
            errors.rejectValue("password","password.incorrect");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirmation", "password.required");
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirmation", "passwordConfirmation.incorrect");
        }

    }
}
