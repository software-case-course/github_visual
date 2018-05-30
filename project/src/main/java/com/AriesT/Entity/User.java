package com.AriesT.Entity;

public class User {

    String user_name;
    String user_location;

    public User(String un,String lo){
        user_name=un;
        user_location=lo;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }
}
