package com.example.project167.Datamodal;

import com.google.gson.annotations.SerializedName;

public class DataModalUpdateProfil {

    @SerializedName("nik")
    private String nikUpdate;
    @SerializedName("nama")
    private String namaUpdate;
//    @SerializedName("lahir")
//    private String lahirUpdate;
//    @SerializedName("darah")
//    private String darahUpdate;
//    @SerializedName("alamat")
//    private String alamatUpdate;
//    @SerializedName("no")
//    private String noUpdate;

    private String status;

    public DataModalUpdateProfil(String nikUpdate, String namaUpdate){
        this.nikUpdate = nikUpdate;
        this.namaUpdate = namaUpdate;
//        this.lahirUpdate = lahirUpdate;
//        this.darahUpdate = darahUpdate;
//        this.alamatUpdate = alamatUpdate;
//        this.noUpdate = noUpdate;
    }


    public String getNikUpdate() {
        return nikUpdate;
    }

    public void setNikUpdate(String nikUpdate) {
        this.nikUpdate = nikUpdate;
    }

    public String getNamaUpdate() {
        return namaUpdate;
    }

    public void setNamaUpdate(String namaUpdate) {
        this.namaUpdate = namaUpdate;
    }

//    public String getLahirUpdate() {
//        return lahirUpdate;
//    }
//
//    public void setLahirUpdate(String lahirUpdate) {
//        this.lahirUpdate = lahirUpdate;
//    }
//
//    public String getDarahUpdate() {
//        return darahUpdate;
//    }
//
//    public void setDarahUpdate(String darahUpdate) {
//        this.darahUpdate = darahUpdate;
//    }
//
//    public String getAlamatUpdate() {
//        return alamatUpdate;
//    }
//
//    public void setAlamatUpdate(String alamatUpdate) {
//        this.alamatUpdate = alamatUpdate;
//    }
//
//    public String getNohpUpdate() {
//        return noUpdate;
//    }
//
//    public void setNohpUpdate(String nohpUpdate) {
//        this.noUpdate = nohpUpdate;
//    }

    public String getStatus() {
        return status;
    }
}
