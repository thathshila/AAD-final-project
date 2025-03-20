package org.example.netwave.service.impl;

import org.example.netwave.dto.ReloadDTO;
import org.example.netwave.entity.CreditBundle;
import org.example.netwave.entity.Offers;
import org.example.netwave.entity.Reload;
import org.example.netwave.repo.Credit_BundleRepo;
import org.example.netwave.repo.ReloadRepo;
import org.example.netwave.service.ReloadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReloadServiceImpl implements ReloadService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Credit_BundleRepo credit_BundleRepo;

    @Autowired
    private ReloadRepo reloadRepo;

    @Override
    public void saveReload(ReloadDTO reloadDTO) {
        CreditBundle creditBundle = credit_BundleRepo.findById(reloadDTO.getCredit_bundle_id())
                .orElseThrow(() -> new RuntimeException("Credit Bundle not found"));
        Reload reload = modelMapper.map(reloadDTO, Reload.class);
        reload.setCreditBundle(creditBundle);
        reload.setReloadDate(LocalDateTime.now());
        reloadRepo.save(reload);
    }

}
