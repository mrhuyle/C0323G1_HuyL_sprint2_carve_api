package com.example.carve.user.service;

import com.example.carve.user.controller.UserController;
import com.example.carve.user.dto.ChangePasswordRequest;
import com.example.carve.user.dto.UserDTO;
import com.example.carve.user.model.Role;
import com.example.carve.user.model.User;

import java.security.Principal;
import java.util.List;

public interface IUserService {
    User saveUser(User user);

    Role saveRole(Role role);

    Role findRoleByName(String roleName);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();

    UserDTO getUserInformation(String username); //Get information of user for front-end


    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    boolean saveImg(String username, String img);
}
