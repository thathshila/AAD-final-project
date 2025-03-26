package org.example.netwave.repo;


import org.example.netwave.entity.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConnectionRepo extends JpaRepository<UserConnection, Integer> {
    List<UserConnection> findByUser_UidAndDeletedFalse(int userId);

    @Query("SELECT u.phoneNumber FROM UserConnection u")
    List<Integer> findAllContacts();
}

