package org.example.netwave.service.impl;

import jakarta.transaction.Transactional;
import org.example.netwave.dto.SupplierDTO;
import org.example.netwave.entity.Supplier;
import org.example.netwave.repo.SupplierRepo;
import org.example.netwave.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public void saveSupplier(SupplierDTO supplierDTO) {
        if (supplierRepo.existsById(supplierDTO.getSupplierId())) {
            throw new RuntimeException("Supplier already exists");
        }
        supplierRepo.save(modelMapper.map(supplierDTO, Supplier.class));
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return modelMapper.map(supplierRepo.findByIsDeletedFalse(),
                new TypeToken<List<SupplierDTO>>() {}.getType());
    }

    @Override
    public Integer getSupplierByName(String name) {
        return supplierRepo.findByName(name);

    }

    @Override
    public List<String> getSupplierNames() {
        return supplierRepo.findAllSupplierNames();
    }


    @Override
    public void updateSupplier(SupplierDTO supplierDTO) {
        if (!supplierRepo.existsById(supplierDTO.getSupplierId())) {
            throw new RuntimeException("Supplier does not exist");
        }
        Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);
        supplierRepo.save(supplier);
    }

    @Override
    public void deleteSupplier(int id) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        supplier.setDeleted(true);
        supplierRepo.save(supplier);
    }

    @Override
    public int getTotalSupplierCount() {
        return supplierRepo.countSuppliers();
    }
}
