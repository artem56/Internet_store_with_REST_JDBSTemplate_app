package com.example.application_dev.Entity;

public class TypeBrandEntity {
    public long id, type_type_id, brand_brand_id;

    public TypeBrandEntity() {
    }

    public TypeBrandEntity(long id, long type_type_id, long brand_brand_id) {
        this.id = id;
        this.type_type_id = type_type_id;
        this.brand_brand_id = brand_brand_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
