package com.example.NoDalal.DTO;

public class LoginRequestDTO {

    public LoginRequestDTO() {}

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String email;
    private String userName;
}
