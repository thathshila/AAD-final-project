package org.example.netwave.controller;

import org.example.netwave.dto.OffersDTO;
import org.example.netwave.service.OffersService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/offers")
public class OffersController {
    @Autowired
    private OffersService offersService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil saveOffers(@RequestBody OffersDTO offersDTO) {
        System.out.println(offersDTO);
        offersService.saveOffer(offersDTO);
        return new ResponseUtil(201,"Offer Create Successfully",null);
    }

    @GetMapping("/getAll")
    public ResponseUtil getAllOffers() {
        return new ResponseUtil(200, "Success",
                offersService.getAllOffers());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil updateOffer(@PathVariable String id, @RequestBody OffersDTO offersDTO) {
        offersDTO.setOfferId(Integer.parseInt(id));  // Assuming 'offerId' is the identifier
        offersService.updateOffer(offersDTO);
        return new ResponseUtil(200, "Offer Updated Successfully", null);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil deleteOffer(@PathVariable int id) {
        offersService.deleteOffer(id);
        return new ResponseUtil(200, "Offer Soft Deleted Successfully", null);
    }
}
