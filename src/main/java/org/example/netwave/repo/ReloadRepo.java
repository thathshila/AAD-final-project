package org.example.netwave.repo;

import org.example.netwave.entity.Reload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReloadRepo extends JpaRepository<Reload, Integer> {
    // Find the most recent reload by sorting by reloadDate in descending order
    Reload findTopByOrderByReloadDateDesc();

    @Query("SELECT SUM(r.amount) FROM Reload r")
    double countReload();
}
