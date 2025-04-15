package org.example.netwave.service.impl;

import jakarta.transaction.Transactional;
import org.example.netwave.advisor.InsufficientBalanceException;
import org.example.netwave.advisor.ResourceNotFoundException;
import org.example.netwave.dto.ReloadDTO;
import org.example.netwave.entity.CreditBundle;
import org.example.netwave.entity.Reload;
import org.example.netwave.repo.Credit_BundleRepo;
import org.example.netwave.repo.ReloadRepo;
import org.example.netwave.service.ReloadService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReloadServiceImpl implements ReloadService {
    @Autowired
    private Credit_BundleRepo credit_BundleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReloadRepo reloadRepo;

    @Override
    public void ReloadService(ReloadRepo reloadRepository, Credit_BundleRepo creditBundleRepository) {
        this.reloadRepo = reloadRepository;
        this.credit_BundleRepo = creditBundleRepository;
    }

    @Override
    public Reload processReload(ReloadDTO reloadDTO) {
        CreditBundle creditBundle = credit_BundleRepo.findById(reloadDTO.getCredit_bundle_id())
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Credit bundle with ID " + reloadDTO.getCredit_bundle_id() + " not found"));

        if (creditBundle.getAmount() < reloadDTO.getAmount()) {
            throw new InsufficientBalanceException("Insufficient amount in credit bundle");
        }

        Reload reload = new Reload();
        reload.setAmount(reloadDTO.getAmount());
        reload.setPhoneNumber(reloadDTO.getPhoneNumber());
        reload.setEmail(reloadDTO.getEmail());
        reload.setReloadDate(LocalDateTime.now());
        reload.setCreditBundle(creditBundle);

        double newAmount = creditBundle.getAmount() - reloadDTO.getAmount();
        creditBundle.setAmount(newAmount);
        credit_BundleRepo.save(creditBundle);
        return reloadRepo.save(reload);
    }

    @Override
    public double getTotalReloadCount() {
        return reloadRepo.countReload();
    }

    @Override
    public List<ReloadDTO> getAllReloads() {
        return modelMapper.map(reloadRepo.findAll(),
                new TypeToken<List<ReloadDTO>>() {}.getType());
    }
}