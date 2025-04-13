package org.example.netwave.service.impl;

import org.example.netwave.dto.ConnectionDTO;
import org.example.netwave.dto.UserDTO;

import org.example.netwave.entity.User;
import org.example.netwave.entity.UserConnection;
import org.example.netwave.repo.ConnectionRepo;
import org.example.netwave.repo.UserRepo;
import org.example.netwave.service.ConnectionService;
import org.example.netwave.service.UserService;
import org.example.netwave.utill.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConnectionRepo connectionRepo;

    @Autowired
    private ConnectionService connectionService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepo.findByEmail(email);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                getAuthority(user));
    }

    public UserDTO loadUserDetailsByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepo.findByEmail(username);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return modelMapper.map(user, UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public UserDTO searchUser(String email) {
        if (userRepo.existsByEmail(email)) {
          //  User user = userRepo.findByEmail(username);
            User user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return modelMapper.map(user, UserDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            User user = modelMapper.map(userDTO, User.class);
            user = userRepo.save(user);

            ConnectionDTO connectionDTO = new ConnectionDTO(user.getUid(), user.getPhoneNumber(), user.getName());
            connectionService.saveConnection(connectionDTO);

            return VarList.Created;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findByDeletedFalse();
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(int id) {
        return userRepo.findById(id)
                .filter(user -> !user.isDeleted())
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElse(null);
    }

    @Override
    public boolean updateUser(int id, UserDTO userDTO) {
        return userRepo.findById(id).map(existingUser -> {
            existingUser.setName(userDTO.getName());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setPhoneNumber(Integer.parseInt(userDTO.getPhoneNumber()));
            userRepo.save(existingUser);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean softDeleteUser(int id) {
        return userRepo.findById(id).map(user -> {
            user.setDeleted(true);
            userRepo.save(user);

            // Soft delete associated connections
            List<UserConnection> connections = connectionRepo.findByUser_UidAndDeletedFalse(id);
            connections.forEach(connection -> {
                connection.setDeleted(true);
                connectionRepo.save(connection);
            });

            return true;
        }).orElse(false);
    }

    public int getTotalUserCount() {
        return userRepo.countUsers();
    }

    @Override
    public boolean emailExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }
}

