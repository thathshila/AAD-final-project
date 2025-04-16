package org.example.netwave.service;

import org.example.netwave.dto.ReloadDTO;
import org.example.netwave.entity.Reload;
import org.example.netwave.repo.Credit_BundleRepo;
import org.example.netwave.repo.ReloadRepo;

import java.util.List;

public interface ReloadService {
    void ReloadService(ReloadRepo reloadRepository, Credit_BundleRepo creditBundleRepository);
    Reload processReload(ReloadDTO reloadDTO);
    double getTotalReloadCount();
    List<ReloadDTO> getAllReloads();
}

