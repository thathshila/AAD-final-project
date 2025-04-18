package org.example.netwave.service.impl;

import jakarta.transaction.Transactional;
import org.example.netwave.dto.Credit_BundleDTO;
import org.example.netwave.entity.CreditBundle;
import org.example.netwave.repo.Credit_BundleRepo;
import org.example.netwave.service.Credit_BundleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Credit_BundleServiceImpl implements Credit_BundleService {

    @Autowired
    private Credit_BundleRepo credit_BundleRepo;

    @Autowired
    private ModelMapper modelMapper;

@Transactional
    @Override
    public void saveCredit_bundle(Credit_BundleDTO creditBundleDTO) {
        if (credit_BundleRepo.existsById(creditBundleDTO.getBundleId())){
            System.out.println(creditBundleDTO.getBundleId());
            throw new RuntimeException("credit bundle already exists");
        }
        System.out.println("Received DTO: " + creditBundleDTO);
        credit_BundleRepo.save(modelMapper.map(creditBundleDTO, CreditBundle.class));
    }

    @Override
    public List<String> getAllNamesCredit_bundle() {
        return credit_BundleRepo.findAllCreditBundleName();
    }

    @Override
    public Integer getCreditBundleByNames(String name) {
        return credit_BundleRepo.findBundleByNames(name);
    }

    @Override
    public List<Credit_BundleDTO> getAllCredit_bundle() {
        return modelMapper.map(credit_BundleRepo.findByIsDeletedFalse(),
                new TypeToken<List<Credit_BundleDTO>>() {}.getType());
    }

    @Override
    public void updateCreditBundle(Credit_BundleDTO creditBundleDTO) {
        if (!credit_BundleRepo.existsById(creditBundleDTO.getBundleId())) {
            throw new RuntimeException("Credit bundle does not exist!");
        }

        CreditBundle creditBundle = modelMapper.map(creditBundleDTO, CreditBundle.class);
        credit_BundleRepo.save(creditBundle);
    }


    @Override
    public void deleteCredit_bundle(int id) {
        CreditBundle creditBundle = credit_BundleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("credit not found"));

        creditBundle.setDeleted(true);
        credit_BundleRepo.save(creditBundle);
    }
}
