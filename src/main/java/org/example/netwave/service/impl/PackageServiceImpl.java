package org.example.netwave.service.impl;

import org.example.netwave.dto.PackageDTO;
import org.example.netwave.entity.CreditBundle;
import org.example.netwave.entity.Packages;
import org.example.netwave.repo.Credit_BundleRepo;
import org.example.netwave.repo.PackageRepo;
import org.example.netwave.service.PackageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepo packageRepo;

    @Autowired
    private Credit_BundleRepo creditBundleRepo; // Fetch CreditBundle

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void savePackage(PackageDTO packageDTO) {
        // Check if package exists
        if (packageDTO.getPackageId() != 0 && packageRepo.existsById(packageDTO.getPackageId())) {
            throw new RuntimeException("Package already exists");
        }

        // Map DTO to entity
        Packages packages = modelMapper.map(packageDTO, Packages.class);

        // Fetch and assign CreditBundle if provided
        if (packageDTO.getCredit_bundle_id() != 0) {
            CreditBundle creditBundle = creditBundleRepo.findById(packageDTO.getCredit_bundle_id())
                    .orElseThrow(() -> new RuntimeException("Credit Bundle not found"));
            packages.setCreditBundle(creditBundle);
        }

        // Save entity
        packageRepo.save(packages);
    }
}
