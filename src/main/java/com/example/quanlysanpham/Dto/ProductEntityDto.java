package com.example.quanlysanpham.Dto;

import com.example.quanlysanpham.Entity.CategoryEntity;
import com.example.quanlysanpham.Entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntityDto {
    private Long id;
    private String name;
    private Double price;
    private Double priceSale;
    private Double percentSale;
    private MultipartFile file;
    private Long categoryId;
    private Integer sl;
    private String image;

    public ProductEntityDto(ProductEntity p){
        this.id = p.getId();
        this.name = p.getName();
        this.price = p.getPrice();
        this.priceSale = p.getPriceSale();
        this.percentSale = p.getPercentSale();
        this.categoryId = p.getCategoryEntity().getId();
        this.sl = 1;
        this.image = p.getImage();
    }
}
