package org.example.netwave.repo;


import org.example.netwave.entity.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ConnectionRepo extends JpaRepository<UserConnection, Integer> {

}

