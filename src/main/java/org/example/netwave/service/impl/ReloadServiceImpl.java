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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@Transactional
public class ReloadServiceImpl implements ReloadService {
    @Autowired
    private Credit_BundleRepo credit_BundleRepo;

    @Autowired
    private ReloadRepo reloadRepo;

    @Override
    public void ReloadService(ReloadRepo reloadRepository, Credit_BundleRepo creditBundleRepository) {
        this.reloadRepo = reloadRepository;
        this.credit_BundleRepo = creditBundleRepository;
    }

    @Override
    public Reload processReload(ReloadDTO reloadDTO) {
        // 1. Find the credit bundle with ID 2
        CreditBundle creditBundle = credit_BundleRepo.findById(reloadDTO.getCredit_bundle_id())
                .orElseThrow(() -> new ResourceNotFoundException("Credit bundle with ID " + reloadDTO.getCredit_bundle_id() + " not found"));

        // 2. Check if credit bundle has enough amount
        if (creditBundle.getAmount() < reloadDTO.getAmount()) {
            throw new InsufficientBalanceException("Insufficient amount in credit bundle");
        }

        // 3. Create and save the reload record
        Reload reload = new Reload();
        reload.setAmount(reloadDTO.getAmount());
        reload.setPhoneNumber(reloadDTO.getPhoneNumber());
        reload.setEmail(reloadDTO.getEmail());
        reload.setReloadDate(LocalDateTime.now());
        reload.setCreditBundle(creditBundle);

        // 4. Update the credit bundle amount
        double newAmount = creditBundle.getAmount() - reloadDTO.getAmount();
        creditBundle.setAmount(newAmount);

        // 5. Save the updated credit bundle
        credit_BundleRepo.save(creditBundle);

        // 6. Save and return the reload
        return reloadRepo.save(reload);
    }

    @Override
    public double getTotalReloadCount() {
        return reloadRepo.countReload();
    }
}