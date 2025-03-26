package org.example.netwave.service.impl;

import org.example.netwave.dto.OffersDTO;
import org.example.netwave.entity.Offers;
import org.example.netwave.entity.Packages;
import org.example.netwave.repo.OffersRepo;
import org.example.netwave.repo.PackageRepo;
import org.example.netwave.service.OffersService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        // Check if the offer already exists
        if (offersDTO.getOfferId() != 0 && offersRepo.existsById(offersDTO.getOfferId())) {
            throw new RuntimeException("Offer already exists");
        }

        // Map DTO to entity
        Offers offers = modelMapper.map(offersDTO, Offers.class);

        // Fetch and assign package
        Packages packages = packageRepo.findById(offersDTO.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));
        offers.setPackages(packages);
        System.out.println(offersDTO.getPackageId());

        // Save to database
        offersRepo.save(offers);
    }

    @Override
    public List<OffersDTO> getAllOffers() {
        List<Offers> offersList = offersRepo.findByIsDeletedFalse();
        return modelMapper.map(offersList, new TypeToken<List<OffersDTO>>() {}.getType());
    }

    @Override
    public void updateOffer(OffersDTO offersDTO) {
        if (!offersRepo.existsById(offersDTO.getOfferId())) {
            throw new RuntimeException("Offer does not exist!");
        }

        // Convert DTO to Entity and Save
        Offers offer = modelMapper.map(offersDTO, Offers.class);

        // Ensure associated package exists
        if (!packageRepo.existsById(offersDTO.getPackageId())) {
            throw new RuntimeException("Package does not exist!");
        }

        offersRepo.save(offer);
    }


    @Override
    public void deleteOffer(int id) {
        Offers offer = offersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setDeleted(true);
        offersRepo.save(offer);
    }
}
