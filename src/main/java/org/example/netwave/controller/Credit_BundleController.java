package org.example.netwave.controller;

import org.example.netwave.dto.Credit_BundleDTO;
import org.example.netwave.service.Credit_BundleService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/credit_bundle")
public class Credit_BundleController {

    @Autowired
    private Credit_BundleService Credit_BundleService;

    @PostMapping("/save")
    public ResponseUtil saveCredit_bundle(@RequestBody Credit_BundleDTO credit_bundleDTO) {
        System.out.println(credit_bundleDTO);
        Credit_BundleService.saveCredit_bundle(credit_bundleDTO);
        return new ResponseUtil(201, "Successfully", null);
    }

    @GetMapping("/getAll")
    public ResponseUtil getAllCredit_bundle() {
        return new ResponseUtil(200,"Get all  Credit Bundles",Credit_BundleService.getAllCredit_bundle());
    }

    @GetMapping("/getNames")
    public ResponseUtil getNamesCredit_bundle() {
        return new ResponseUtil(200,"Get all  Credit Bundles",Credit_BundleService.getAllNamesCredit_bundle());
    }

    @GetMapping("/getCreditByName/{name}")
    public ResponseUtil getBundleByName(@PathVariable String name) {
        Integer bundleId = Credit_BundleService.getCreditBundleByNames(name);
        return new ResponseUtil(200, "Bundle found",bundleId);
    }

    @PutMapping("update")
    public ResponseUtil updateCredit_bundle(@RequestBody Credit_BundleDTO credit_bundleDTO) {
        Credit_BundleService.updateCredit_bundle(credit_bundleDTO);
        return new ResponseUtil(201, "updated", null);
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseUtil deleteCredit_bundle(@PathVariable int id) {
        Credit_BundleService.deleteCredit_bundle(id);
        return new ResponseUtil(200, "Credit_bundle deleted successfully (soft delete)", null);
    }

}
