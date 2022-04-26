package com.example.module_javaspringboot_day20_01_loginwithfbandgg.repository;

import com.example.module_javaspringboot_day20_01_loginwithfbandgg.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity,Long> {
}
