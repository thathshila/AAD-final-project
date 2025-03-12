package org.example.netwave.repo;

import org.example.netwave.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
    List<Admin> findByIsDeletedFalse();
}
