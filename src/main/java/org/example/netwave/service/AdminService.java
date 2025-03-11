package org.example.netwave.service;

import org.example.netwave.dto.AdminDTO;

import java.util.List;

public interface AdminService {
    void saveAdmin(AdminDTO adminDTO);

    List<AdminDTO> getAllAdmins();

    void updateAdmin(AdminDTO adminDTO);

    void deleteAdmin(int id);
}
