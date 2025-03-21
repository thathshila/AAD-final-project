package org.example.netwave.repo;


import org.example.netwave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {

    User findByEmail(String userName);

    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);

   // User findByEmail(String email);
  //  boolean existsByEmail(String email);
    List<User> findByDeletedFalse(); // Fetch only non-deleted users

}