package com.example.application_dev.Entity;

public class DeviceInfoEntity {
    public long id, device_device_id;
    private String title;



    private String description;

    public DeviceInfoEntity() {
    }

    public DeviceInfoEntity(long id, String title, String description, long device_device_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.device_device_id = device_device_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public long getDevice_device_id() {
        return device_device_id;
    }

    public void setDevice_device_id(long device_device_id) {
        this.device_device_id = device_device_id;
    }
}
