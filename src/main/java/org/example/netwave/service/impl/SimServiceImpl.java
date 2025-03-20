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

    private String generateUniqueSIMNumber() {
        String simNumber;
        do {
          simNumber = "052" + String.format("%07d", Math.abs(random.nextLong()) % 1_000_0000L);
        } while (simRepo.existsBySimNumber(simNumber));
        return simNumber;
    }

    @Override
    public void createSIM(SimDTO simDTO) {
        SIM sim = modelMapper.map(simDTO, SIM.class);
        sim.setSimNumber(generateUniqueSIMNumber());
        simRepo.save(sim);
    }
}
