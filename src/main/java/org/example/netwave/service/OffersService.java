package org.example.netwave.service;

import org.example.netwave.dto.OffersDTO;

import java.util.List;

public interface OffersService {
    void saveOffers(OffersDTO offersDTO);
    List<OffersDTO> getAllOffers(); // Fetch all active offers
    void updateOffer(OffersDTO offersDTO); // Update offer
    void deleteOffer(int id); // Soft delete

}
