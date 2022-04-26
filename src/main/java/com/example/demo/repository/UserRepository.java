package com.example.demo.repository;

import com.example.demo.entity.ProviderEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Enumerated;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByUsername(String username);
    @Query("select u from UserEntity u where u.username like ?1 and u.provider = ?2")
    UserEntity findByUsernameAndProvider(String name , ProviderEntity provider);
}
