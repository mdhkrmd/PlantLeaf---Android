package com.example.project167.Activity;

//import com.example.donorin.DataModalRegister;

import com.example.project167.Datamodal.DataModalForgot;
import com.example.project167.Datamodal.DataModalRegister;
import com.example.project167.Datamodal.DataModalLogin;
import com.example.project167.Datamodal.DataModalUpdateProfil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {

    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("register")
    Call<DataModalRegister> createPost(@Body DataModalRegister dataModal);
//
    @POST("login")
    Call<DataModalLogin> createPostLogin(@Body DataModalLogin dataModalLogin);
//
    @GET("users")
    Call<List<DataModalLogin>> getLoginData(
            @Query("nik") String nik
    );
//
    @POST("forgot")
    Call<DataModalForgot> createPostForgot(@Body DataModalForgot dataModalForgot);

//    @POST("daftar")
//    Call<DataModalDaftar> createPostDaftar(@Body DataModalDaftar dataModalDaftar);
//
    @POST("update")
    Call<DataModalUpdateProfil> createPostUpdate(@Body DataModalUpdateProfil dataModalUpdateProfil);
//
//    @GET("darah-darurat")
//    Call<List<DataModalDarahDarurat>> getDataDarahDaruratList();
//
//    @POST("tambah-darah-darurat")
//    Call<DataModalDarahDarurat> createDarahDarurat(@Body DataModalDarahDarurat dataModalDarahDarurat);
//
//    @POST("daftar-donor-darurat")
//    Call<DataModalDaftar> createPostDaftarDonorDarurat(@Body DataModalDaftar dataModalDaftar);
//
//
    @GET("tanaman")
    Call<List<TanamanData>> getTanaman();
//
//    @GET("rspmi/detail")
//    Call<List<RspmiData>> getRspmiDetail(
//            @Query("id") int id
//    );
//
//    @GET("riwayat")
//    Call<List<riwayatData>> getRiwayat(
//            @Query("nik") String nik
//    );

    @GET("artikel")
    Call<List<ArtikelData>> getArtikel();

}
