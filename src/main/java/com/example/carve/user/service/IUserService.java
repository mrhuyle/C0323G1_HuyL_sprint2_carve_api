package com.example.carve.user.service;

import com.example.carve.user.Role;
import com.example.carve.user.User;

import java.util.List;

public interface IUserService {
    User saveUser(User user);
    Role saveRole(Role role);
    Role findRoleByName(String roleName);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
