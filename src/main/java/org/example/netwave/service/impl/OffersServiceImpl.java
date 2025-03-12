package org.example.netwave.service.impl;

import org.example.netwave.dto.OffersDTO;
import org.example.netwave.entity.Offers;
import org.example.netwave.entity.Packages;
import org.example.netwave.repo.OffersRepo;
import org.example.netwave.repo.PackageRepo;
import org.example.netwave.service.OffersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OffersServiceImpl implements OffersService {

    @Autowired
    private OffersRepo offersRepo;

    @Autowired
    private PackageRepo packageRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveOffers(OffersDTO offersDTO) {
        // Map DTO to entity
        Offers offers = modelMapper.map(offersDTO, Offers.class);

        // Fetch and assign package
        Packages packages = packageRepo.findById(offersDTO.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));
        offers.setPackages(packages);

        // Save to database
        offersRepo.save(offers);
    }
}
