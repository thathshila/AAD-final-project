package org.example.netwave.controller;

import org.example.netwave.dto.OffersDTO;
import org.example.netwave.service.OffersService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/offers")
public class OffersController {
    @Autowired
    private OffersService offersService;

    @PostMapping("save")
    public ResponseUtil saveOffers(@RequestBody OffersDTO offersDTO) {
        offersService.saveOffers(offersDTO);
        return new ResponseUtil(201,"Offer Create Successfully",null);
    }
}
