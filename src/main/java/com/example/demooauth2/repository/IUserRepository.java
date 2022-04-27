package com.example.demooauth2.repository;

import com.example.demooauth2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String name);
    UserEntity findByToken(String token);
}
