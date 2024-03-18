package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;
import static com.example.project167.Activity.MainActivity.storeNik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project167.Adapter.riwayatAdapter;
import com.example.project167.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_riwayat extends AppCompatActivity {

    RecyclerView rvRiwayat;
    TextView btnRspmi, btnProfil, btnUtama;
    FloatingActionButton btnScan;
    TextView ambilNama, ambilNik;
    private static final String PREFS_NAME = "YourPrefsFile";
    private static final String KEY_NIK = "nik";
    private String nik2, nama2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        rvRiwayat = findViewById(R.id.rvRiwayat);
        btnScan = findViewById(R.id.btnScan);
        btnProfil = findViewById(R.id.textView10);
        btnUtama = findViewById(R.id.textView101);
        ambilNama = findViewById(R.id.ambilNama);
        ambilNik = findViewById(R.id.ambilNik);

        setStatusBarColor(activity_riwayat.this);

        String nik = getStoredNik2();

        // Jika nik belum tersimpan (nilai default), ambil dari intent
        if (nik.equals("default_value")) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("nik")) {
                nik = intent.getStringExtra("nik");

                // Simpan nik ke SharedPreferences
                storeNik(activity_riwayat.this, nik);
            }
        }
        getRiwayat(nik);

        Intent intent = getIntent();
        nik2 = intent.getStringExtra("nik");
        nama2 = intent.getStringExtra("nama");

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_riwayat.this, activity_predict.class);
                intent.putExtra("nik", nik2.toString());
                intent.putExtra("nama", nama2.toString());
                startActivity(intent);
//                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(open_camera, 100);
            }
        });
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPindah = new Intent(activity_riwayat.this, activity_profil.class);

                startActivity(intentPindah);
            }
        });
//
        btnUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPindah = new Intent(activity_riwayat.this, MainActivity.class);
                startActivity(intentPindah);
            }
        });
    }
    private String getStoredNik2() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return preferences.getString(KEY_NIK, "default_value");
    }

    private void getRiwayat(String nik) {
        Call<List<riwayatData>> apiCall =  RetroServer.getRetrofitAPI().getRiwayat(nik);
        apiCall.enqueue(new Callback<List<riwayatData>>() {
            @Override
            public void onResponse(Call<List<riwayatData>> call, Response<List<riwayatData>> response) {
                List<riwayatData> riwayatDataList = response.body();
                Toast.makeText(activity_riwayat.this, "Riwayat terambil", Toast.LENGTH_SHORT).show();
                setAdapter(riwayatDataList);
            }

            @Override
            public void onFailure(Call<List<riwayatData>> call, Throwable t) {
                Toast.makeText(activity_riwayat.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(List<riwayatData> riwayatDataList) {
        rvRiwayat.setLayoutManager(new LinearLayoutManager(this));
        riwayatAdapter riwayatAdapter = new riwayatAdapter(this, riwayatDataList);
        rvRiwayat.setAdapter(riwayatAdapter);
    }
}