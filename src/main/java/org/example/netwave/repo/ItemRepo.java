package org.example.netwave.repo;

import org.example.netwave.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Integer> {
    Optional<Item> findByName(String name);
    List<Item> findByIsDeletedFalse();

    @Query("SELECT COUNT(i) FROM Item i WHERE i.isDeleted = false")
    int countItem();
}

