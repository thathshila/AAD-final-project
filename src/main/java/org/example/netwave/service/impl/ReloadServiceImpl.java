package org.example.netwave.service.impl;

import org.example.netwave.dto.ReloadDTO;
import org.example.netwave.entity.Packages;
import org.example.netwave.entity.Reload;
import org.example.netwave.repo.ReloadRepo;
import org.example.netwave.service.ReloadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReloadServiceImpl implements ReloadService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReloadRepo reloadRepo;

    @Override
    public void saveReload(ReloadDTO reloadDTO) {
        reloadRepo.save(modelMapper.map(reloadDTO, Reload.class));
    }
}
