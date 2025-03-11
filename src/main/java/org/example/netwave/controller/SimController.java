package org.example.netwave.controller;

import org.example.netwave.dto.SimDTO;
import org.example.netwave.service.SimService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/sim")
public class SimController {

    @Autowired
    private SimService simService;

    @PostMapping("save")
    private ResponseUtil createSim(@RequestBody SimDTO simDTO) {
        simService.createSIM(simDTO);
        return new ResponseUtil(201, "SIM Created Successfully", simDTO);
    }
}
