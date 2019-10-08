package com.santikdef.notebook.service;

import com.santikdef.notebook.model.User;

public interface UserService {
    void save(User user);

    User getByUsername(String username);

    User getCurrentUser();
}
