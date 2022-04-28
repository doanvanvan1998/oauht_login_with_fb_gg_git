package com.example.quanlysanpham.Dto;

public class CartDto {
    private Long id;
    private Integer sl;

    public CartDto(Long id, Integer sl) {
        this.id = id;
        this.sl = sl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public CartDto() {
    }

}
