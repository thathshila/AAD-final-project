package org.example.netwave.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.netwave.dto.PackageActivationRequest;
import org.example.netwave.entity.ActivePackage;
import org.example.netwave.repo.ActivePackageRepo;
import org.example.netwave.service.ActivePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivePackageServiceImpl implements ActivePackageService {

    @Autowired
    private ActivePackageRepo activePackageRepository;

    @Transactional
    @Override
    public ActivePackage activatePackage(PackageActivationRequest request) {
        // Check if there is an existing active package of the same type
        List<ActivePackage> existingPackages = activePackageRepository
                .findActivePackagesByPhoneNumberAndPackageId(request.getPhoneNumber(), request.getPackageId());

        // If exists, deactivate the old one
        for (ActivePackage existingPackage : existingPackages) {
            existingPackage.setIsActive(false);
            existingPackage.setUpdatedAt(LocalDateTime.now());
            activePackageRepository.save(existingPackage);
        }

        // Create new active package
        ActivePackage newPackage = new ActivePackage();
        newPackage.setPackageId(request.getPackageId());
        newPackage.setPackageName(request.getPackageName());
        newPackage.setPrice(request.getPrice());
        newPackage.setPhoneNumber(request.getPhoneNumber());

        // If activation date is provided, use it, otherwise it'll default to now
        if (request.getActivationDate() != null) {
            newPackage.setActivationDate(request.getActivationDate());
            // Set expiry date 30 days from activation
            newPackage.setExpiryDate(request.getActivationDate().plusDays(30));
        }

        // Save to database
        return activePackageRepository.save(newPackage);
    }

    @Override
    public List<ActivePackage> getActivePackagesByPhoneNumber(String phoneNumber) {
        return activePackageRepository.findByPhoneNumberAndIsActiveTrue(phoneNumber);
    }

    @Override
    public List<ActivePackage> getAllPackagesByPhoneNumber(String phoneNumber) {
        return activePackageRepository.findByPhoneNumber(phoneNumber);
    }
}