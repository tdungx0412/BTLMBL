package com.example.dienlanh24h;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String id;
    private String userId;
    private String itemName;
    private String itemType;
    private String price;
    private int quantity;
    private String totalAmount;
    private String status;
    private String paymentMethod;
    private String date;
    private String address;
    private String phone;
    private String customerName;
    private String serviceDate;
    private String serviceTime;
    private String notes;

    public Transaction() {}

    public Transaction(String id, String userId, String itemName, String itemType,
                       String price, int quantity, String totalAmount, String status,
                       String paymentMethod, String date, String address,
                       String phone, String customerName, String serviceDate,
                       String serviceTime, String notes) {
        this.id = id;
        this.userId = userId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.price = price;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.address = address;
        this.phone = phone;
        this.customerName = customerName;
        this.serviceDate = serviceDate;
        this.serviceTime = serviceTime;
        this.notes = notes;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getTotalAmount() { return totalAmount; }
    public void setTotalAmount(String totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getServiceDate() { return serviceDate; }
    public void setServiceDate(String serviceDate) { this.serviceDate = serviceDate; }

    public String getServiceTime() { return serviceTime; }
    public void setServiceTime(String serviceTime) { this.serviceTime = serviceTime; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getItemTypeLabel() {
        return itemType.equals("service") ? "Dịch vụ" : "Sản phẩm";
    }
}