package org.example.netwave.controller;

import jakarta.validation.Valid;
import org.example.netwave.dto.Credit_BundleDTO;
import org.example.netwave.dto.ResponseDTO;
import org.example.netwave.service.Credit_BundleService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/credit_bundle")
public class Credit_BundleController {

    @Autowired
    private Credit_BundleService Credit_BundleService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil saveCredit_bundle(@RequestBody Credit_BundleDTO credit_bundleDTO) {
        System.out.println(credit_bundleDTO);
        Credit_BundleService.saveCredit_bundle(credit_bundleDTO);
        return new ResponseUtil(201, "Successfully", null);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil getAllCredit_bundle() {
        return new ResponseUtil(200,"Get all  Credit Bundles",
                Credit_BundleService.getAllCredit_bundle());
    }

    @GetMapping("/getNames")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil getNamesCredit_bundle() {
        return new ResponseUtil(200,"Get all  Credit Bundles",
                Credit_BundleService.getAllNamesCredit_bundle());
    }

    @GetMapping("/getCreditByName/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil getBundleByName(@PathVariable String name) {
        Integer bundleId = Credit_BundleService.getCreditBundleByNames(name);
        return new ResponseUtil(200, "Bundle found",bundleId);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil updateCreditBundle(@PathVariable String id, @RequestBody Credit_BundleDTO creditBundleDTO) {
        creditBundleDTO.setBundleId(Integer.parseInt(id));
        Credit_BundleService.updateCreditBundle(creditBundleDTO);
        return new ResponseUtil(200, "Credit bundle updated successfully", null);
    }


    @DeleteMapping(path = "/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil deleteCredit_bundle(@PathVariable int id) {
        Credit_BundleService.deleteCredit_bundle(id);
        return new ResponseUtil(200, "Credit_bundle deleted successfully (soft delete)", null);
    }

}
