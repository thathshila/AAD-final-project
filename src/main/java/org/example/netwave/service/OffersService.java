package org.example.netwave.service;

import org.example.netwave.dto.OffersDTO;

import java.util.List;

public interface OffersService {
    void saveOffers(OffersDTO offersDTO);
    List<OffersDTO> getAllOffers();
    void updateOffer(OffersDTO offersDTO);
    void deleteOffer(int id);

}
