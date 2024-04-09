package com.example.application_dev.Entity;

public class BrandEntity {
    public long id;
    private String name;

    public BrandEntity () {
    }
    public BrandEntity (long id, String name) {
        this.id = id;
        this.name = name;
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
}
