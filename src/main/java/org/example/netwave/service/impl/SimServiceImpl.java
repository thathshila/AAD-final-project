package org.example.netwave.service.impl;

import org.example.netwave.dto.SimDTO;
import org.example.netwave.entity.SIM;
import org.example.netwave.repo.SimRepo;
import org.example.netwave.service.SimService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SimServiceImpl implements SimService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SimRepo simRepo;

    private static final SecureRandom random = new SecureRandom();

    // Generate a unique 10-digit SIM number
    private String generateUniqueSIMNumber() {
        String simNumber;
        do {
            // Generate a random 7-digit number and prepend "052" to it
            simNumber = "052" + String.format("%07d", Math.abs(random.nextLong()) % 1_000_0000L);
        } while (simRepo.existsBySimNumber(simNumber)); // Ensure uniqueness
        return simNumber;
    }

    // Create a new SIM and assign a number
    @Override
    public void createSIM(SimDTO simDTO) {
        SIM sim = modelMapper.map(simDTO, SIM.class);
        sim.setSimNumber(generateUniqueSIMNumber()); // Set unique number
        simRepo.save(sim);
    }
}
