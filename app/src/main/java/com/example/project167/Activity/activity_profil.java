package com.example.project167.Activity;

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

import com.example.project167.R;
import com.example.project167.Datamodal.DataModalLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_profil extends AppCompatActivity {

    TextView ambilNama, txtUpdate, txtLogout, ambilNik;
    TextView halamanUtama;

    private static final String PREFS_NAME = "YourPrefsFile";
    private static final String KEY_NIK = "nik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        ambilNama = findViewById(R.id.ambilNama);
        ambilNik = findViewById(R.id.ambilNik);
        txtUpdate = findViewById(R.id.txtUpdate);
        txtLogout = findViewById(R.id.txtLogout);
        halamanUtama = findViewById(R.id.textView101);

        String nik = getStoredNik3();

        // Jika nik belum tersimpan (nilai default), ambil dari intent
        if (nik.equals("default_value")) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("nik")) {
                nik = intent.getStringExtra("nik");

                // Simpan nik ke SharedPreferences
                storeNik(activity_profil.this, nik);
            }
        }

        getDataLogin(nik);

        halamanUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPindah = new Intent(activity_profil.this, MainActivity.class);

                startActivity(intentPindah);
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeNik(activity_profil.this, "default_value");
                activity_login.clearLoginPreferences(activity_profil.this);
                Intent intentPindah = new Intent(activity_profil.this, activity_login.class);
                startActivity(intentPindah);
                finish();
            }
        });
    }
    private String getStoredNik3() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return preferences.getString(KEY_NIK, "default_value");
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

                        Toast.makeText(activity_profil.this, "Data Login terambil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity_profil.this, "Login data is null or empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity_profil.this, "Failed to get login data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DataModalLogin>> call, Throwable t) {
                Toast.makeText(activity_profil.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}