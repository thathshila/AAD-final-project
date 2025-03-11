package org.example.netwave.controller;


import org.example.netwave.dto.ReloadDTO;
import org.example.netwave.service.ReloadService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/reload")
public class ReloadController {

    @Autowired
    private ReloadService reloadService;

    @PostMapping("save")
    public ResponseUtil saveReload(@RequestBody ReloadDTO reloadDTO) {
        reloadService.saveReload(reloadDTO);
        return new ResponseUtil(201,"Reload Created Successfully",null);
    }
}
