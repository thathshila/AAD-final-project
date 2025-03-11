package org.example.netwave.service.impl;

import org.example.netwave.dto.AdminDTO;
import org.example.netwave.entity.Admin;
import org.example.netwave.repo.AdminRepo;
import org.example.netwave.service.AdminService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private ModelMapper modelMapper;

//    @Override
//    public void saveAdmin(AdminDTO adminDTO) {
//        if (adminRepo.existsById(adminDTO.getAdminId())){
//             throw new RuntimeException("Admin already exists");
//        }
//            adminRepo.save(modelMapper.map(adminDTO, Admin.class));
//    }
//    @Override
//    public void updateAdmin(AdminDTO adminDTO) {
//        if (adminRepo.existsById(adminDTO.getAdminId())){
//             adminRepo.save(modelMapper.map(adminDTO, Admin.class));
//        }
//            throw new RuntimeException("Admin does not exists");
//    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void saveAdmin(AdminDTO adminDTO) {
        if (adminRepo.existsById(adminDTO.getAdminId())) {
            throw new RuntimeException("Admin already exists");
        }

        // Convert DTO to Entity
        Admin admin = modelMapper.map(adminDTO, Admin.class);

        // Hash the password before saving
        admin.setAdminPassword(passwordEncoder.encode(adminDTO.getAdminPassword()));

        adminRepo.save(admin);
    }

    @Override
    public void updateAdmin(AdminDTO adminDTO) {
        if (adminRepo.existsById(adminDTO.getAdminId())) {
            Admin admin = modelMapper.map(adminDTO, Admin.class);

            // Encrypt password before updating if it's changed
            if (adminDTO.getAdminPassword() != null && !adminDTO.getAdminPassword().isEmpty()) {
                admin.setAdminPassword(passwordEncoder.encode(adminDTO.getAdminPassword()));
            }

            adminRepo.save(admin);
        } else {
            throw new RuntimeException("Admin does not exist");
        }
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        return modelMapper.map(adminRepo.findAll(),
                new TypeToken<List<AdminDTO>>() {}.getType());

    }


    @Override
    public void deleteAdmin(int id) {
        adminRepo.deleteById(id);
    }
}
