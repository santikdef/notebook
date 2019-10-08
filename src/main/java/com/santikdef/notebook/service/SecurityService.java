package com.santikdef.notebook.service;


public interface SecurityService {
    String getUsername();

    void autoLogin(String username, String password);
}
