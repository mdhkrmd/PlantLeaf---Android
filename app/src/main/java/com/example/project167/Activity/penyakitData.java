package com.example.project167.Activity;

import com.google.gson.annotations.SerializedName;

public class penyakitData {
    @SerializedName("id")
    private int idPenyakit;
    @SerializedName("label_penyakit")
    private String label_penyakit;
    @SerializedName("tentang_penyakit")
    private String tentang_penyakit;
    @SerializedName("gejala")
    private String gejala;
    @SerializedName("penanganan")
    private String penanganan;
    @SerializedName("gambar")
    private String gambar_penyakit;

    public int getIdPenyakit() {
        return idPenyakit;
    }

    public String getLabel_penyakit() {
        return label_penyakit;
    }

    public String getTentang_penyakit() {
        return tentang_penyakit;
    }

    public String getGejala_penyakit() {
        return gejala;
    }

    public String getPenanganan() {
        return penanganan;
    }

    public String getGambar_penyakit() {
        return gambar_penyakit;
    }
}
