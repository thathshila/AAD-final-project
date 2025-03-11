package org.example.netwave.repo;

import org.example.netwave.entity.SIM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimRepo extends JpaRepository<SIM, Integer> {
    boolean existsBySimNumber(String simNumber);
}
