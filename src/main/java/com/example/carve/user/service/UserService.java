package com.example.carve.user.service;

import com.example.carve.cart.service.ICartService;
import com.example.carve.user.dto.ChangePasswordRequest;
import com.example.carve.user.dto.UserDTO;
import com.example.carve.user.model.Role;
import com.example.carve.user.model.User;
import com.example.carve.user.repository.RankUserRepository;
import com.example.carve.user.repository.RoleRepository;
import com.example.carve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ICartService cartService;
    private final PasswordEncoder passwordEncoder;
    private final RankUserRepository rankUserRepository;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        cartService.createCart(user);
        user.setRank(rankUserRepository.findById(1L).get());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding user {} and role {} to the database", username, roleName);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public UserDTO getUserInformation(String username) {
        return userRepository.findByUsernameWithRank(username);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // Check the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // Check the new pass and confirmation pass are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        //Set new pass
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean saveImg(String username, String img) {
        var user = userRepository.findByUsername(username);
        if (user != null) {
            user.setImg(img);
            return true;
        }
        return false;
    }
}
