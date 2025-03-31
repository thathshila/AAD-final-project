package org.example.netwave.controller;


import org.example.netwave.dto.SupplierDTO;
import org.example.netwave.utill.ResponseUtil;
import org.example.netwave.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        supplierService.saveSupplier(supplierDTO);
        return new ResponseUtil(201, "Supplier saved successfully", null);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        return new ResponseUtil(200, "Retrieved all suppliers", suppliers);
    }

    @GetMapping("/getByName/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil getSupplierByName(@PathVariable String name) {
        Integer supplierId = supplierService.getSupplierByName(name);
        return new ResponseUtil(200, "Supplier found", supplierId);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil updateSupplier(@PathVariable int id, @RequestBody SupplierDTO supplierDTO) {
        supplierDTO.setSupplierId(id);
        supplierService.updateSupplier(supplierDTO);
        return new ResponseUtil(200, "Supplier updated successfully", null);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseUtil deleteSupplier(@PathVariable int id) {
        supplierService.deleteSupplier(id);
        return new ResponseUtil(200, "Supplier deleted successfully (soft delete)", null);
    }
}
