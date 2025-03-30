package org.example.netwave.service;

import org.example.netwave.dto.OffersDTO;
import org.example.netwave.entity.Offers;

import java.util.List;

public interface OffersService {
    void saveOffer(OffersDTO offersDTO);
    List<OffersDTO> getAllOffers();
    void updateOffer(OffersDTO offersDTO);
    void deleteOffer(int id);

}
