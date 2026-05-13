package models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequest {

    private String title;
    private String description;
    private float price;
    private float discountPercentage;
    private float rating;
    private int stock;
    private String brand;
    private String category;
    private String thumbnail;
}