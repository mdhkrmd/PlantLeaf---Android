package com.example.project167.Activity;

import com.google.gson.annotations.SerializedName;

public class ArtikelData {
    @SerializedName("id")
    private int idArtikel;
    @SerializedName("judul")
    private String judulArtikel;
    @SerializedName("penulis")
    private String penulisArtikel;
    @SerializedName("foto")
    private String fotoArtikel;
    @SerializedName("link")
    private String linkArtikel;

    public int getIdArtikel() {
        return idArtikel;
    }

    public String getPenulisArtikel() {
        return penulisArtikel;
    }

    public String getJudulArtikel() {
        return judulArtikel;
    }

    public String getFotoArtikel() {
        return fotoArtikel;
    }

    public String getLinkArtikel() {
        return linkArtikel;
    }
}
