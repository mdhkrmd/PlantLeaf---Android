package com.example.project167.Activity;
import com.google.gson.annotations.SerializedName;

public class riwayatData {
    @SerializedName("id")
    private String idRiwayat;
    @SerializedName("nik")
    private String nikRiwayat;
    @SerializedName("tanggal")
    private String tanggalRiwayat;
    @SerializedName("penyakit")
    private String penyakitRiwayat;
    @SerializedName("nama")
    private String namaRiwayat;
    @SerializedName("gambar")
    private String gambarRiwayat;
    @SerializedName("catatan")
    private String catatan;

    public String getIdRiwayat() {
        return idRiwayat;
    }
    public void setIdRiwayat(String idRiwayat) {
        this.idRiwayat = idRiwayat;
    }

    public String getNikRiwayat() {
        return nikRiwayat;
    }
    public void setNikRiwayat(String nikRiwayat) {
        this.nikRiwayat = nikRiwayat;
    }

    public String getNamaRiwayat() {
        return namaRiwayat;
    }
    public void setNamaRiwayat(String namaRiwayat) {
        this.namaRiwayat = namaRiwayat;
    }

    public String getTanggalRiwayat() {
        return tanggalRiwayat;
    }

    public void setTanggalRiwayat(String tanggalRiwayat) {
        this.tanggalRiwayat = tanggalRiwayat;
    }

    public String getPenyakitRiwayat() {
        return penyakitRiwayat;
    }
    public String getCatatanRiwayat() {
        return catatan;
    }

    public void setPenyakitRiwayat(String penyakitRiwayat) {
        this.penyakitRiwayat = penyakitRiwayat;
    }

    public String getGambarRiwayat() {
        return gambarRiwayat;
    }

    public void setGambarRiwayat(String gambarRiwayat) {
        this.gambarRiwayat = gambarRiwayat;
    }
}
