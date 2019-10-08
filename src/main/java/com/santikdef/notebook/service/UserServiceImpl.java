package com.santikdef.notebook.service;

import com.santikdef.notebook.model.Role;
import com.santikdef.notebook.model.User;
import com.santikdef.notebook.repository.RoleRepository;
import com.santikdef.notebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SecurityService securityService;


    @Override
    public void save(User user) {
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ROLE_USER));

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
    }


    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getCurrentUser() {
        String username = securityService.getUsername();
        if (username != null) {
            return getByUsername(username);
        }
        return null;
    }
}
