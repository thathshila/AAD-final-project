package org.example.netwave.repo;

import org.example.netwave.entity.Offers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffersRepo extends JpaRepository<Offers, Integer> {
    List<Offers> findByIsDeletedFalse();
}
