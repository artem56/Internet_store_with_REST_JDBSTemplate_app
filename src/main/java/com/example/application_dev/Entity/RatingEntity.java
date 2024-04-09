package com.example.application_dev.Entity;

public class RatingEntity {
    public long id;



    public long user_user_id;
    private int rate;

    public RatingEntity () {
    }
    public RatingEntity (long id, int rate, long user_user_id) {
        this.id = id;
        this.rate = rate;
        this.user_user_id = user_user_id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int rate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
    public long getUser_user_id() {
        return user_user_id;
    }

    public void setUser_user_id(long user_user_id) {
        this.user_user_id = user_user_id;
    }
}
