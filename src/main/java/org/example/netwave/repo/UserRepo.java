package org.example.netwave.repo;


import org.example.netwave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String userName);


    List<User> findByDeletedFalse();

    @Query("SELECT COUNT(u) FROM User u WHERE u.deleted = false")
    int countUsers();


}