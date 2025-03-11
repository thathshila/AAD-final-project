package org.example.netwave.service.impl;

import org.example.netwave.dto.PackageDTO;
import org.example.netwave.entity.Packages;
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
    private ModelMapper modelMapper;

    @Override
    public void savePackage(PackageDTO packageDTO) {
        if (packageRepo.existsById(packageDTO.getPackageId())){
            throw new RuntimeException("Package already exists");
        }
        packageRepo.save(modelMapper.map(packageDTO, Packages.class));
    }
}
