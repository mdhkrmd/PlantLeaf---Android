package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project167.R;
import com.example.project167.Datamodal.DataModalLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_login extends AppCompatActivity {

    private EditText inputUsername, inputPassword;
    private Button btnLogin;
    private CheckBox chkBox;
    private TextView txtForgot, txtDaftar;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtForgot = findViewById(R.id.txtLupa);
        txtDaftar = findViewById(R.id.tvDaftar);
        chkBox = findViewById(R.id.checkBoxIngat);

        // Mendapatkan preferensi login
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        // Mengatur nilai checkbox sesuai preferensi
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        chkBox.setChecked(saveLogin);

        // Jika checkbox tercentang, lakukan auto-login
        if (saveLogin) {
            String savedUsername = loginPreferences.getString("username", "");
            String savedPassword = loginPreferences.getString("password", "");
            postDataLogin(savedUsername, savedPassword);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (inputUsername.getText().toString().trim().length()==0
                        || inputPassword.getText().toString().trim().length()==0) {
                    Toast.makeText(activity_login.this, "Mohon lengkapi yang masih kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    postDataLogin(inputUsername.getText().toString(),
                            inputPassword.getText().toString());
                }
            }
        });

        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aksi yang akan diambil saat TextView (txtForgot) diklik
                // Misalnya, pindah ke halaman baru menggunakan Intent

                Intent intent = new Intent(activity_login.this, activity_lupa.class);
                // Gantilah NamaActivitySaatIni dan NamaActivityTujuan dengan nama aktivitas sebenarnya

                // Jika Anda ingin membawa data tambahan ke aktivitas tujuan, Anda dapat menggunakan putExtra
                // intent.putExtra("key", "value");

                startActivity(intent);
            }
        });

        txtDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aksi yang akan diambil saat TextView (txtForgot) diklik
                // Misalnya, pindah ke halaman baru menggunakan Intent

                Intent intent = new Intent(activity_login.this, activity_register.class);
                // Gantilah NamaActivitySaatIni dan NamaActivityTujuan dengan nama aktivitas sebenarnya

                // Jika Anda ingin membawa data tambahan ke aktivitas tujuan, Anda dapat menggunakan putExtra
                // intent.putExtra("key", "value");

                startActivity(intent);
            }
        });

        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Menyimpan status checkbox ke preferensi
                loginPrefsEditor.putBoolean("saveLogin", isChecked);
                loginPrefsEditor.apply();
            }
        });

        setStatusBarColor(activity_login.this);
    }

    public static void clearLoginPreferences(Context context) {
        SharedPreferences loginPreferences = context.getSharedPreferences("loginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
        loginPrefsEditor.clear();
        loginPrefsEditor.apply();
    }

    private void postDataLogin(String username, String password) {

        DataModalLogin modal = new DataModalLogin(username, password);
        Call<DataModalLogin> call = RetroServer.getRetrofitAPI().createPostLogin(modal);

        if (chkBox.isChecked()) {
            loginPrefsEditor.putString("username", username);
            loginPrefsEditor.putString("password", password);
            loginPrefsEditor.apply();
        }
        call.enqueue(new Callback<DataModalLogin>() {
            @Override
            public void onResponse(Call<DataModalLogin> call, Response<DataModalLogin> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataModalLogin responseFromAPI = response.body();

                    // Assuming there is a "status" field in the response indicating success
                    if (responseFromAPI.getStatus().equals("success")) {
                        Toast.makeText(activity_login.this, "Berhasil Login", Toast.LENGTH_SHORT).show();

                        inputUsername.setText("");
                        inputPassword.setText("");

                        String kirimusername = responseFromAPI.getUsername();
                        String nik = responseFromAPI.getNik();
                        String nama = responseFromAPI.getNama();

                        // Assuming Utama.class is the target activity
                        Intent intent = new Intent(activity_login.this, MainActivity.class);

                        intent.putExtra("username", kirimusername);
                        intent.putExtra("nik", nik);
                        intent.putExtra("nama", nama);

                        startActivity(intent);
                    } else {
                        Toast.makeText(activity_login.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response (HTTP error, network issues, etc.)
                    Toast.makeText(activity_login.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataModalLogin> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Tambahkan logika untuk menutup aplikasi atau melakukan aksi khusus
        // Misalnya, keluar dari aplikasi saat tombol "back" ditekan setelah login
        super.onBackPressed();
        finishAffinity(); // Menutup semua aktivitas di dalam tumpukan
    }

}