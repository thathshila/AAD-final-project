package org.example.netwave.service;

import org.example.netwave.dto.PackageActivationRequest;
import org.example.netwave.entity.ActivePackage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActivePackageService {
    @Transactional
    ActivePackage activatePackage(PackageActivationRequest request);

    List<ActivePackage> getActivePackagesByPhoneNumber(String phoneNumber);

    List<ActivePackage> getAllPackagesByPhoneNumber(String phoneNumber);
}
