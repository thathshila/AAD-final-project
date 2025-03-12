package org.example.netwave.service.impl;

import org.example.netwave.dto.Credit_BundleDTO;
import org.example.netwave.entity.Admin;
import org.example.netwave.entity.CreditBundle;
import org.example.netwave.repo.AdminRepo;
import org.example.netwave.repo.Credit_BundleRepo;
import org.example.netwave.service.Credit_BundleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Credit_BundleDTO> getAllCredit_bundle() {
        return modelMapper.map(credit_BundleRepo.findAll(),
                new TypeToken<List<Credit_BundleDTO>>() {}.getType());
    }

    @Override
    public void updateCredit_bundle(Credit_BundleDTO creditBundleDTO) {
        CreditBundle existingCreditBundle = credit_BundleRepo.findById(creditBundleDTO.getCredit_bundle_id())
                .orElseThrow(() -> new RuntimeException("Credit Bundle not found"));

        // Fetch the Admin entity using the provided admin_id
        Admin admin = adminRepo.findById(creditBundleDTO.getAdmin_id())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Update the fields
        existingCreditBundle.setBundleName(creditBundleDTO.getBundleName());
        existingCreditBundle.setAmount(creditBundleDTO.getAmount());
        existingCreditBundle.setAdmin(admin);

        // Save the updated entity
        credit_BundleRepo.save(existingCreditBundle);
    }
}
