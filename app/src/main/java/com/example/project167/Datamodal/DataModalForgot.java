package com.example.project167.Datamodal;

public class DataModalForgot {

    private String username;
    private String new_password;
    private String status;

    public DataModalForgot(String username, String new_password) {
        this.username = username;
        this.new_password = new_password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewpassword() {
        return new_password;
    }

    public void setNewpassword(String newpassword) {
        this.new_password = newpassword;
    }

    public String getStatus() {
        return status;
    }
}
