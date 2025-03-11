package org.example.netwave.controller;

import org.example.netwave.dto.PackageDTO;
import org.example.netwave.service.PackageService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/packages")
public class packageController {

    @Autowired
    private PackageService packageService;

    @PostMapping("save")
    public ResponseUtil savePackage(@RequestBody PackageDTO packageDTO) {
        packageService.savePackage(packageDTO);
        return new ResponseUtil(201,"Package Created Successfully",null);
    }

}
