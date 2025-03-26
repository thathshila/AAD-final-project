package org.example.netwave.service.impl;

import org.example.netwave.dto.ConnectionDTO;
import org.example.netwave.entity.UserConnection;
import org.example.netwave.entity.User;
import org.example.netwave.repo.ConnectionRepo;
import org.example.netwave.repo.UserRepo;
import org.example.netwave.service.ConnectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConnectionServiceImpl implements ConnectionService{
    @Autowired
    private ConnectionRepo connectionRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveConnection(ConnectionDTO connectionDTO) {
        User user = userRepo.findById(Integer.valueOf(String.valueOf(connectionDTO.getUserId()))).orElseThrow(
                () -> new RuntimeException("User not found"));
        UserConnection connection = new UserConnection();
        connection.setUser(user);
        connection.setPhoneNumber(connectionDTO.getPhoneNumber());
        connection.setUserName(connectionDTO.getUserName());

        connectionRepo.save(connection);
    }

    @Override
    public List<Integer> getAllConnections() {
        return connectionRepo.findAllContacts();
    }
}
