package org.example.netwave.repo;

import org.example.netwave.entity.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRepo extends JpaRepository<Packages, Integer> {
    List<Packages> findByIsDeletedFalse();

    @Query("SELECT p.packageName FROM Packages p")
    List<String> findAllPackageNames();

    @Query("SELECT p.packageId FROM Packages p WHERE p.packageName =:name")
    Integer findPackageIdByNames(String name);

    @Query("SELECT p FROM Packages p WHERE p.packageType = :packageType AND p.isDeleted = false")
    List<Packages> findByPackageTypeAndIsDeletedFalse(@Param("packageType") String packageType);

    List<Packages> findByPackageType(String packageType);

}
