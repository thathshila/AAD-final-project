package org.example.netwave.service;

import org.example.netwave.dto.Credit_BundleDTO;

import java.util.List;

public interface Credit_BundleService {
    void saveCredit_bundle(Credit_BundleDTO creditBundleDTO);

    List<Credit_BundleDTO> getAllCredit_bundle();

    void updateCredit_bundle(Credit_BundleDTO creditBundleDTO);

    void deleteCredit_bundle(int id);
}
