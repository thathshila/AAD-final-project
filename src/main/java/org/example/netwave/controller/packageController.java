package org.example.netwave.controller;

import org.example.netwave.dto.PackageDTO;
import org.example.netwave.service.PackageService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("getAll")
    public ResponseUtil getAllPackages() {
        List<PackageDTO> packageDTOList = packageService.getAllPackages();
        return new ResponseUtil(200, "Packages Retrieved Successfully", packageDTOList);
    }

    // Get package by ID (GET)
    @GetMapping("get/{id}")
    public ResponseUtil getPackageById(@PathVariable int id) {
        PackageDTO packageDTO = packageService.getPackageById(id);
        return new ResponseUtil(200, "Package Retrieved Successfully", packageDTO);
    }

    // Update a package (PUT)
    @PutMapping("update/{id}")
    public ResponseUtil updatePackage(@PathVariable int id, @RequestBody PackageDTO packageDTO) {
        packageService.updatePackage(id, packageDTO);
        return new ResponseUtil(200, "Package Updated Successfully", null);
    }

    // Soft delete a package (DELETE)
    @DeleteMapping("delete/{id}")
    public ResponseUtil deletePackage(@PathVariable int id) {
        packageService.deletePackage(id);
        return new ResponseUtil(200, "Package Deleted Successfully", null);
    }
}
