package org.example.netwave.controller;

import org.example.netwave.dto.Credit_BundleDTO;
import org.example.netwave.service.Credit_BundleService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/credit_bundle")
public class Credit_BundleController {

    @Autowired
    private Credit_BundleService Credit_BundleService;

    @PostMapping("save")
    public ResponseUtil saveCredit_bundle(@RequestBody Credit_BundleDTO credit_bundleDTO) {
        Credit_BundleService.saveCredit_bundle(credit_bundleDTO);
        return new ResponseUtil(201, "Successfully", null);
    }

    @GetMapping("getAll")
    public ResponseUtil getAllCredit_bundle() {
        return new ResponseUtil(200,"Get all  Credit Bundles",Credit_BundleService.getAllCredit_bundle());
    }
}
