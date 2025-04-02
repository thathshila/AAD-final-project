package org.example.netwave.repo;

import org.example.netwave.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierRepo extends JpaRepository<Supplier, Integer> {

    List<Supplier> findByIsDeletedFalse();

    @Query("SELECT s.supplierId FROM Supplier s WHERE s.name =:name")
    Integer findByName(String name);

    @Query("SELECT COUNT(s) FROM Supplier s WHERE s.isDeleted = false")
    int countSuppliers();

    @Query("SELECT s.name FROM Supplier s")
    List<String> findAllSupplierNames();

}
