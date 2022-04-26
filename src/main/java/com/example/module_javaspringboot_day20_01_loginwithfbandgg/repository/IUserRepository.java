package com.example.module_javaspringboot_day20_01_loginwithfbandgg.repository;

import com.example.module_javaspringboot_day20_01_loginwithfbandgg.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);

}
