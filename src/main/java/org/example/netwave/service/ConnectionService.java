package org.example.netwave.service;

import org.example.netwave.dto.ConnectionDTO;


import java.util.List;

public interface ConnectionService {
    void saveConnection(ConnectionDTO connectionDTO);

    List<Integer> getAllContacts();

}

