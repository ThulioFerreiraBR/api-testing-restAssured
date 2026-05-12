package models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequest {

    private String title;
    private double price;
    private int stock;
    private String category;
}