package org.example.netwave.repo;

import org.example.netwave.entity.Packages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepo extends JpaRepository<Packages, Integer> {
}
