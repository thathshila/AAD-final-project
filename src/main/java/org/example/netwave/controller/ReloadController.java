package org.example.netwave.controller;


import org.example.netwave.advisor.InsufficientBalanceException;
import org.example.netwave.advisor.ResourceNotFoundException;
import org.example.netwave.dto.ReloadDTO;
import org.example.netwave.entity.Reload;
import org.example.netwave.service.ReloadService;

import org.example.netwave.utill.ErrorResponse;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/v1/reload")
@CrossOrigin(origins = "*") // For development - restrict this in production
public class ReloadController {

    private final ReloadService reloadService;

    @Autowired
    public ReloadController(ReloadService reloadService) {
        this.reloadService = reloadService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createReload(@RequestBody ReloadDTO reloadDTO) {
        try {
            Reload savedReload = reloadService.processReload(reloadDTO);
            return new ResponseEntity<>(savedReload, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InsufficientBalanceException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("An error occurred while processing the reload: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseUtil getAllReloads() {
        return new ResponseUtil(200, "Get all Reloads", reloadService.getAllReloads());
    }
}