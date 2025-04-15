package org.example.netwave.controller;

import org.example.netwave.dto.PackageDTO;
import org.example.netwave.entity.Packages;
import org.example.netwave.service.PackageService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/packages")
public class packageController {

    @Autowired
    private PackageService packageService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil savePackage(@RequestBody PackageDTO packageDTO) {
        System.out.println(packageDTO);
        packageService.savePackage(packageDTO);
        return new ResponseUtil(201, "Package saved successfully!", null);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil updatePackage(@PathVariable String id, @RequestBody PackageDTO packageDTO) {
        packageDTO.setPackageId(Integer.parseInt(id));
        packageService.updatePackage(packageDTO);
        return new ResponseUtil(200, "Package updated successfully", null);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil deletePackage(@PathVariable String id) {
        packageService.deletePackage(Integer.parseInt(id));
        return new ResponseUtil(200, "Package deleted successfully", null);
    }

    @GetMapping("/getAll")
    public ResponseUtil getAllPackages() {
        return new ResponseUtil(200, "Get all Packages", packageService.getAllPackages());
    }

    @GetMapping("get/{id}")
    public ResponseUtil getPackageById(@PathVariable int id) {
        PackageDTO packageDTO = packageService.getPackageById(id);
        return new ResponseUtil(200, "Package Retrieved Successfully", packageDTO);
    }

    @GetMapping("/getNames")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil getPackageNames() {
        return new ResponseUtil(200, "Package Names Retrieved Successfully",
                packageService.getPackageNames());
    }

    @GetMapping("/getPackageId/{name}")
    public ResponseUtil getBundleByName(@PathVariable String name) {
        Integer packageId = packageService.getPackageIdByNames(name);
        return new ResponseUtil(200, "Bundle found",packageId);
    }

    @GetMapping("/getByType/{packageType}")
    public ResponseEntity<Map<String, Object>> getPackagesByType(@PathVariable String packageType) {
        List<PackageDTO> packageDTOs = packageService.getPackagesByType(packageType);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", packageDTOs);
        return ResponseEntity.ok(response);
    }


}
