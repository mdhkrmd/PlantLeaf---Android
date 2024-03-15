package com.example.project167.Activity;

import com.google.gson.annotations.SerializedName;

public class TanamanData {
    @SerializedName("id")
    private int idTanaman;
    @SerializedName("nama_tanaman")
    private String nama_tanaman;
    @SerializedName("tentang")
    private String tentang;
    @SerializedName("merawat")
    private String merawat;
    @SerializedName("gambar")
    private String gambar;

    public int getidTanaman() {
        return idTanaman;
    }

    public String getnama_tanaman() {
        return nama_tanaman;
    }

    public String gettentang() {
        return tentang;
    }

    public String getmerawat() {
        return merawat;
    }

    public String getgambar() {
        return gambar;
    }
}