package org.example.netwave.repo;

import org.example.netwave.entity.Reload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReloadRepo extends JpaRepository<Reload, Integer> {
    // Find the most recent reload by sorting by reloadDate in descending order
    Reload findTopByOrderByReloadDateDesc();
}
