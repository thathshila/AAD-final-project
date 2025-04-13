package org.example.netwave.repo;


import org.example.netwave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
   //User findByEmail(String email);

   // User findByEmail(String userName);
   Optional<User> findByEmailAndDeletedFalse(String email);
    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);
   // User findByEmail(String email);
  //  boolean existsByEmail(String email);
    List<User> findByDeletedFalse(); // Fetch only non-deleted users

    @Query("SELECT COUNT(u) FROM User u WHERE u.deleted = false")
    int countUsers();


}