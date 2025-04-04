package org.example.netwave.service.impl;

import org.example.netwave.dto.PackageDTO;
import org.example.netwave.entity.Packages;
import org.example.netwave.entity.User;
import org.example.netwave.repo.Credit_BundleRepo;
import org.example.netwave.repo.PackageRepo;
import org.example.netwave.repo.UserRepo;
import org.example.netwave.service.EmailService;
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
    private Credit_BundleRepo creditBundleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    //    @Override
//    public void savePackage(PackageDTO packageDTO) {
//        if (packageRepo.existsById(packageDTO.getPackageId())) {
//            throw new RuntimeException("Package already exists!");
//        }
//        packageRepo.save(modelMapper.map(packageDTO, Packages.class));
//    }
    @Override
    public void savePackage(PackageDTO packageDTO) {
        if (packageRepo.existsById(packageDTO.getPackageId())) {
            throw new RuntimeException("Package already exists!");
        }

        // Map DTO to Entity and save package
        Packages newPackage = modelMapper.map(packageDTO, Packages.class);
        packageRepo.save(newPackage);

        // Fetch all registered users
        List<User> users = userRepo.findAll();

        // Send email notifications
        for (User user : users) {
            emailService.sendPackageNotification(
                    user.getEmail(),
                    newPackage.getPackageName(),
                    newPackage.getPackageType(),
                    newPackage.getPrice(),
                    newPackage.getDataLimit(),
                    newPackage.getCallMinutes(),
                    newPackage.getSmsLimit(),
                    newPackage.getValidityDays()
            );
        }
    }

    @Override
    public void updatePackage(PackageDTO packageDTO) {
        if (!packageRepo.existsById(packageDTO.getPackageId())) {
            throw new RuntimeException("Package does not exist!");
        }
        Packages packageEntity = modelMapper.map(packageDTO, Packages.class);
        packageRepo.save(packageEntity);
    }

    @Override
    public void deletePackage(int packageId) {
        Packages packageItem = packageRepo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found!"));

        packageItem.setDeleted(true);  // Mark as deleted (soft delete)
        packageRepo.save(packageItem);  // Save changes
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
    public List<String> getPackageNames() {
        return packageRepo.findAllPackageNames();
    }

    @Override
    public Integer getPackageIdByNames(String name) {
        return packageRepo.findPackageIdByNames(name);
    }

    @Override
    public List<PackageDTO> getPackagesByType(String type) {
        return packageRepo.findByPackageTypeAndIsDeletedFalse(type).stream()
                .map(Packages -> modelMapper.map(Packages, PackageDTO.class))
                .collect(Collectors.toList());
    }
}

