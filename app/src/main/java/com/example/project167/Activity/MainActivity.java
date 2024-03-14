package com.example.project167.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project167.Adapter.PopularAdapter;
import com.example.project167.Datamodal.DataModalLogin;
import com.example.project167.R;
import com.example.project167.databinding.ActivityMainBinding;
import com.example.project167.domain.PopularDomain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Button buttonChoose;
    FloatingActionButton btnScan;
    private RecyclerView recyclerViewTrends, rvArtikel;
    private Uri imageUri;
    private static final int CAMERA_REQUEST_CODE = 101;

//    SwipeRefreshLayout swipeRefreshLayout;

    private static final String PREFS_NAME = "YourPrefsFile";
    private static final String KEY_NIK = "nik";
    TextView ambilNik, ambilNama, txtProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        buttonChoose = findViewById(R.id.buttonChoose);
        btnScan = findViewById(R.id.btnScan);
        rvArtikel = findViewById(R.id.PopularView);
        ambilNik = findViewById(R.id.textView);
        ambilNama = findViewById(R.id.textView2);
        txtProfil = findViewById(R.id.textView10);

         statusBarColor();
//         initRecyclerView();
         bottomNavigation();

         getArtikel();

        String nik = getStoredNik();

        // Jika nik belum tersimpan (nilai default), ambil dari intent
        if (nik.equals("default_value")) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("nik")) {
                nik = intent.getStringExtra("nik");

                // Simpan nik ke SharedPreferences
                storeNik(MainActivity.this, nik);
            }
        }

        getDataLogin(nik);

//        buttonChoose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, activity_predict.class);
//                startActivity(intent);
//            }
//        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_predict.class);
                startActivity(intent);
//                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(open_camera, 100);
            }
        });

        txtProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPindah = new Intent(MainActivity.this, activity_profil.class);
                intentPindah.putExtra("nik", ambilNik.getText().toString());
                intentPindah.putExtra("nama", ambilNama.getText().toString());
                startActivity(intentPindah);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open the camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } else {
                // Permission denied
                Toast.makeText(this, "Camera permission is required to use the camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getArtikel() {
        Call<List<ArtikelData>> apiCall =  RetroServer.getRetrofitAPI().getArtikel();
        apiCall.enqueue(new Callback<List<ArtikelData>>() {
            @Override
            public void onResponse(Call<List<ArtikelData>> call, Response<List<ArtikelData>> response) {
                List<ArtikelData> artikelDataList = response.body();
//                Toast.makeText(utama.this, "Artikel terambil", Toast.LENGTH_SHORT).show();
                setAdapter(artikelDataList);
            }

            @Override
            public void onFailure(Call<List<ArtikelData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setAdapter(List<ArtikelData> artikelDataList) {
        rvArtikel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArtikelAdapter artikelAdapter = new ArtikelAdapter(this, artikelDataList);
        rvArtikel.setAdapter(artikelAdapter);
    }

    private void bottomNavigation() {
        binding.cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));
    }

    private void statusBarColor() {
        Window window=MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.purple_Dark));
    }
    public static void storeNik(Context context, String nik) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NIK, nik);
        editor.apply();
    }

    private String getStoredNik() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return preferences.getString(KEY_NIK, "default_value");
    }

    @Override
    public void onBackPressed() {
        // Tambahkan logika untuk menutup aplikasi atau melakukan aksi khusus
        // Misalnya, keluar dari aplikasi saat tombol "back" ditekan setelah login
        super.onBackPressed();
        storeNik(MainActivity.this, "default_value");
        finishAffinity(); // Menutup semua aktivitas di dalam tumpukan
    }

    private void getDataLogin(final String nik) {
        Call<List<DataModalLogin>> apiCall = RetroServer.getRetrofitAPI().getLoginData(nik);
        apiCall.enqueue(new Callback<List<DataModalLogin>>() {
            @Override
            public void onResponse(Call<List<DataModalLogin>> call, Response<List<DataModalLogin>> response) {
                if (response.isSuccessful()) {
                    List<DataModalLogin> loginData = response.body();

                    // Check if the login data is not null
                    if (loginData != null && !loginData.isEmpty()) {
                        // Assuming getNama() is a method in DataModalLogin to get the name

                        String nik = loginData.get(0).getNik();
                        String nama = loginData.get(0).getNama();

                        // Assuming ambilNama is a TextView

                        ambilNik.setText(nik);
                        ambilNama.setText(nama);

                        Toast.makeText(MainActivity.this, "Data Login terambil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Login data is null or empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get login data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DataModalLogin>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initRecyclerView() {
        ArrayList<PopularDomain> items=new ArrayList<>();
        items.add(new PopularDomain("Artikel 1","item_1",15,4,500,"Immerse yourself in a world of vibrant visuals and\n" +
                " immersive sound with the monitor.\n" +
                " Its cutting-edge monitor technology brings every\n" +
                " scene to life with striking clarity and rich colors.\n" +
                " With seamless integration and a sleek, modern\n" +
                " design, the monitor Pro is not just a monitor , but a\n" +
                " centerpiece for your entertainment space.\n" +
                "With its sleek, modern design, the monitor is\n" +
                " not just a TV, but a centerpiece for your \n" +
                "entertainment space. The ultra-slim bezel and\n" +
                " premium finish blend seamlessly with any decor"));
        items.add(new PopularDomain("Artikel 2","item_2",10,4.5,450,"Immerse yourself in a world of vibrant visuals and\n" +
                " immersive sound with the monitor.\n" +
                " Its cutting-edge monitor technology brings every\n" +
                " scene to life with striking clarity and rich colors.\n" +
                " With seamless integration and a sleek, modern\n" +
                " design, the monitor Pro is not just a monitor , but a\n" +
                " centerpiece for your entertainment space.\n" +
                "With its sleek, modern design, the monitor is\n" +
                " not just a TV, but a centerpiece for your \n" +
                "entertainment space. The ultra-slim bezel and\n" +
                " premium finish blend seamlessly with any decor"));
        items.add(new PopularDomain("Artikel 3","item_3",3,4.9,800,"Immerse yourself in a world of vibrant visuals and\n" +
                " immersive sound with the monitor.\n" +
                " Its cutting-edge monitor technology brings every\n" +
                " scene to life with striking clarity and rich colors.\n" +
                " With seamless integration and a sleek, modern\n" +
                " design, the monitor Pro is not just a monitor , but a\n" +
                " centerpiece for your entertainment space.\n" +
                "With its sleek, modern design, the monitor is\n" +
                " not just a TV, but a centerpiece for your \n" +
                "entertainment space. The ultra-slim bezel and\n" +
                " premium finish blend seamlessly with any decor"));

        binding.PopularView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.PopularView.setAdapter(new PopularAdapter(items));
    }
}
