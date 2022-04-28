package com.example.quanlysanpham.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "priceSale")
    private Double priceSale;

    @Column(name = "percentSale")
    private Double percentSale;

    @Column(name = "image")
    private String image;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private CategoryEntity categoryEntity;
}
