package org.example.netwave.service;


import org.example.netwave.dto.UserDTO;

public interface UserService {
    UserDTO searchUser(String username);

    int saveUser(UserDTO userDTO);
}