package org.example.netwave.service.impl;

import org.example.netwave.dto.Credit_BundleDTO;
import org.example.netwave.entity.Admin;
import org.example.netwave.entity.CreditBundle;
import org.example.netwave.repo.AdminRepo;
import org.example.netwave.repo.Credit_BundleRepo;
import org.example.netwave.service.Credit_BundleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Credit_BundleServiceImpl implements Credit_BundleService {

    @Autowired
    private Credit_BundleRepo credit_BundleRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveCredit_bundle(Credit_BundleDTO creditBundleDTO) {
        CreditBundle creditBundle = modelMapper.map(creditBundleDTO, CreditBundle.class);

        // Fetch the Admin entity using the provided admin_id
        Admin admin = adminRepo.findById(creditBundleDTO.getAdmin_id())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        creditBundle.setAdmin(admin);

        // Save the entity
        credit_BundleRepo.save(creditBundle);
    }
}
