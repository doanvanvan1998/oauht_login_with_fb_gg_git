package com.example.demo_oauth2.Entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_name")
    private String role_name;
    @ManyToMany(targetEntity = UserEntity.class)
    private Set<UserEntity> userEntities = new HashSet<>();
}
