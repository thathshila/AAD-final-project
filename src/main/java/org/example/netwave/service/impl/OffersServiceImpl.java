package org.example.netwave.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
public void saveOffer(OffersDTO offersDTO) {
    if (offersRepo.existsById(offersDTO.getOfferId())) {
        throw new RuntimeException("Offer already exists!");
    }
    Packages packages = packageRepo.findById(offersDTO.getPackageId())
            .orElseThrow(() -> new RuntimeException("Package not found"));
    Offers offer = modelMapper.map(offersDTO, Offers.class);
    offer.setPackages(packages);
    offersRepo.save(offer);
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
        Offers offer = modelMapper.map(offersDTO, Offers.class);
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
