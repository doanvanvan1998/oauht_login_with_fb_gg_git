package com.example.demologinfacebookgoogle.repository;

import com.example.demologinfacebookgoogle.entiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUsername(String u);
}
