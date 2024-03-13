package com.example.project167.Datamodal;

public class DataModalRegister {

    // string variables for our name and job
    private String username;
    private String password;
    private String nik;
    private String nama;

    public DataModalRegister(String username, String password, String nik, String nama) {
        this.username = username;
        this.password = password;
        this.nik = nik;
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}

