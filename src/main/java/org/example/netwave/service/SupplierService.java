package org.example.netwave.service;

import jakarta.transaction.Transactional;
import org.example.netwave.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {
    @Transactional
    void saveSupplier(SupplierDTO supplierDTO);

    List<SupplierDTO> getAllSuppliers();

    Integer getSupplierByName(String name);

    void updateSupplier(SupplierDTO supplierDTO);

    void deleteSupplier(int id);

    int getTotalSupplierCount();
}
