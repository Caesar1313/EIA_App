package com.example.eia_app.models;

public class LoginResponseModel {

    String jwtToken;

    Long userId;

    public String getJwtToken() {
        return jwtToken;
    }

    public Long getUserId(){
        return userId;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
