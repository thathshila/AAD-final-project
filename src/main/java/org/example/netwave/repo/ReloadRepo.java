package org.example.netwave.repo;

import org.example.netwave.entity.Reload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReloadRepo extends JpaRepository<Reload, Integer> {
    @Query("SELECT SUM(r.amount) FROM Reload r")
    double countReload();
}
