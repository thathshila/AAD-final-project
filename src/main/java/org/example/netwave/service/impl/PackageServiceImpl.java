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

import java.util.List;
import java.util.stream.Collectors;

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
        if (packageDTO.getPackageId() != 0 && packageRepo.existsById(packageDTO.getPackageId())) {
            throw new RuntimeException("Package already exists");
        }
        Packages packages = modelMapper.map(packageDTO, Packages.class);
        if (packageDTO.getCredit_bundle_id() != 0) {
            CreditBundle creditBundle = creditBundleRepo.findById(packageDTO.getCredit_bundle_id())
                    .orElseThrow(() -> new RuntimeException("Credit Bundle not found"));
            packages.setCreditBundle(creditBundle);
        }
        packageRepo.save(packages);
    }
    @Override
    public List<PackageDTO> getAllPackages() {
        return packageRepo.findByIsDeletedFalse().stream()
                .map(packageEntity -> modelMapper.map(packageEntity, PackageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PackageDTO getPackageById(int id) {
        Packages packageEntity = packageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
        return modelMapper.map(packageEntity, PackageDTO.class);
    }

    @Override
    public void updatePackage(int id, PackageDTO packageDTO) {
        Packages packageEntity = packageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        packageEntity.setPackageName(packageDTO.getPackageName());
        packageEntity.setPackageType(packageDTO.getPackageType());
        packageEntity.setDataLimit(packageDTO.getDataLimit());
        packageEntity.setCallMinutes(packageDTO.getCallMinutes());
        packageEntity.setSmsLimit(packageDTO.getSmsLimit());
        packageEntity.setPrice(packageDTO.getPrice());
        packageEntity.setValidityDays(packageDTO.getValidityDays());

        if (packageDTO.getCredit_bundle_id() != 0) {
            CreditBundle creditBundle = creditBundleRepo.findById(packageDTO.getCredit_bundle_id())
                    .orElseThrow(() -> new RuntimeException("Credit Bundle not found"));
            packageEntity.setCreditBundle(creditBundle);
        }

        packageRepo.save(packageEntity);
    }

    @Override
    public void deletePackage(int id) {
        Packages packageEntity = packageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        packageEntity.setDeleted(true);  // Soft delete
        packageRepo.save(packageEntity);
    }
}
