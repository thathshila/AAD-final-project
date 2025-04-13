package org.example.netwave.service;


import org.example.netwave.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO searchUser(String username);
    int saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(int id);
    boolean updateUser(int id, UserDTO userDTO);
    boolean softDeleteUser(int id);

    int getTotalUserCount();

    boolean emailExists(String email);
}