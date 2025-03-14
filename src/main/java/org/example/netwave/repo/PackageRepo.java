package org.example.netwave.repo;

import org.example.netwave.entity.Packages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageRepo extends JpaRepository<Packages, Integer> {
    List<Packages> findByIsDeletedFalse();
}
