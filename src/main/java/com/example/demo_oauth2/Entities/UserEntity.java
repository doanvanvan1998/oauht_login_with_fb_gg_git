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
@Table(name = "tbl_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = RoleEntity.class)
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            name = "user_role"
    )
    private Set<RoleEntity> roleEntities = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private Provider provider;

}
