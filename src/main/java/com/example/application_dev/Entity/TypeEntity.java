package com.example.application_dev.Entity;

public class TypeEntity {
   public long id;
   private String name;

    public TypeEntity() {
    }
    public TypeEntity(long id, String name) {
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
