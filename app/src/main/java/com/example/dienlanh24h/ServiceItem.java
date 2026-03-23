package com.example.dienlanh24h;

import java.io.Serializable;

public class ServiceItem implements Serializable {
    private String id;
    private String name;
    private String price;
    private String phone;
    private String description;
    private int imageResource;
    private String category;

    // Constructor rỗng (bắt buộc cho Gson)
    public ServiceItem() {}

    // Constructor đầy đủ
    public ServiceItem(String id, String name, String price, String phone,
                       String description, int imageResource, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.phone = phone;
        this.description = description;
        this.imageResource = imageResource;
        this.category = category;
    }

    // === GETTERS ===
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getPhone() { return phone; }
    public String getDescription() { return description; }
    public int getImageResource() { return imageResource; }
    public String getCategory() { return category; }

    // === SETTERS ===
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(String price) { this.price = price; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDescription(String description) { this.description = description; }
    public void setImageResource(int imageResource) { this.imageResource = imageResource; }
    public void setCategory(String category) { this.category = category; }
}