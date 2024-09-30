package com.security.system.service;

import com.security.system.exception.ResourceNotFoundException;
import com.security.system.model.Role;
import com.security.system.model.User;
import com.security.system.repository.RoleRepository;
import com.security.system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // CRUD - C
    public User addUserRole(Long userId, Long roleIdToAdd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Role roleToAdd = roleRepository.findById(roleIdToAdd)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found."));

        if (!user.getRoles().contains(roleToAdd)) {
            user.getRoles().add(roleToAdd);
        }

        return userRepository.save(user);
    }

    // CRUD - R
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    // CRUD - U


    // CRUD - D
    public User removeUserRole(Long userId, Long roleIdToRemove) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Role roleToRemove = roleRepository.findById(roleIdToRemove)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found."));

        user.getRoles().remove(roleToRemove);

        return userRepository.save(user);
    }

}
