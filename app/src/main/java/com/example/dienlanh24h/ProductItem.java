package com.example.dienlanh24h;

import java.io.Serializable;

public class ProductItem implements Serializable {
    private String id;
    private String name;
    private String price;
    private String description;
    private int imageResource;
    private String category;
    private int stock;

    public ProductItem() {}

    public ProductItem(String id, String name, String price, String description,
                       int imageResource, String category, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResource = imageResource;
        this.category = category;
        this.stock = stock;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getImageResource() { return imageResource; }
    public void setImageResource(int imageResource) { this.imageResource = imageResource; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}