package com.shoppit.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private long id;
    private String name;
    private String description;
    private double price;
    private int quantity;

}
