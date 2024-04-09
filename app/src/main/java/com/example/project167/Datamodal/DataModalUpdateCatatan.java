package com.example.project167.Datamodal;

import com.google.gson.annotations.SerializedName;

public class DataModalUpdateCatatan {
    @SerializedName("id")
    private String id_catatan;
    @SerializedName("catatan")
    private String catatan;

    public DataModalUpdateCatatan(String id_catatan, String catatan){
        this.id_catatan = id_catatan;
        this.catatan = catatan;
    }

    private String status;

    public String getId_catatan() {
        return id_catatan;
    }

    public void setId_catatan(String id_catatan) {
        this.id_catatan = id_catatan;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getStatus() {
        return status;
    }
}
