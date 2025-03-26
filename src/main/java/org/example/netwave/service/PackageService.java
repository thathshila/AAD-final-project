package org.example.netwave.service;

import org.example.netwave.dto.PackageDTO;

import java.util.List;

public interface PackageService {
    void savePackage(PackageDTO packageDTO);
    List<PackageDTO> getAllPackages();
    PackageDTO getPackageById(int id);
    void updatePackage(PackageDTO packageDTO);
    void deletePackage(int id);
    List<String> getPackageNames();
    Integer getPackageIdByNames(String name);
}
