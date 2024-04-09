package com.example.application_dev.Entity;

public class DeviceEntity {
    public long id;
    public long type_type_id;
    public long brand_brand_id;
    private String name;
    private String img;

    private String info;
    private int price, rating;
    public DeviceEntity() {

    }
    public DeviceEntity(long id, String name, String img, int price, int rating, long type_type_id, long brand_brand_id) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.price = price;
        this.rating = rating;
        this.type_type_id = type_type_id;
        this.brand_brand_id = brand_brand_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getType_type_id() {
        return type_type_id;
    }

    public void setType_type_id(long type_type_id) {
        this.type_type_id = type_type_id;
    }

    public long getBrand_brand_id() {
        return brand_brand_id;
    }

    public void setBrand_brand_id(long brand_brand_id) {
        this.brand_brand_id = brand_brand_id;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}


