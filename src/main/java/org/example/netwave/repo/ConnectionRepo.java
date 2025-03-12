package org.example.netwave.repo;


import org.example.netwave.entity.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRepo extends JpaRepository<UserConnection, Integer> {
    List<UserConnection> findByUser_UidAndDeletedFalse(int userId);
}

