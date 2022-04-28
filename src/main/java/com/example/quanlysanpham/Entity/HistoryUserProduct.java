package com.example.quanlysanpham.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "histoty_user_product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryUserProduct extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private ProductEntity productEntity;
}
