package org.example.netwave.repo;

import org.example.netwave.entity.Admin;
import org.example.netwave.entity.CreditBundle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Credit_BundleRepo extends JpaRepository<CreditBundle,Integer> {
    List<CreditBundle> findByIsDeletedFalse();
}
