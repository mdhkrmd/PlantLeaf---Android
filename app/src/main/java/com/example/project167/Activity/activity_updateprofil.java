package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project167.Datamodal.DataModalUpdateProfil;
import com.example.project167.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_updateprofil extends AppCompatActivity {

    EditText editNik, editNama;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofil);

        editNik = findViewById(R.id.editNik);
        editNama = findViewById(R.id.editNama);
        btnUpdate = findViewById(R.id.btnUpdate);

        Intent intent = getIntent();
        editNik.setText(intent.getStringExtra("nik"));
        editNama.setText(intent.getStringExtra("nama"));

        setStatusBarColor(activity_updateprofil.this);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (editNik.getText().toString().trim().isEmpty()
                        || editNama.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity_updateprofil.this, "Mohon lengkapi yang masih kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    postDataUpdate(editNik.getText().toString(),
                            editNama.getText().toString());
                }
            }
        });
    }

    private void postDataUpdate(String nikUpdate, String namaUpdate) {

        // passing data from our text fields to our modal class.
        DataModalUpdateProfil modal = new DataModalUpdateProfil(nikUpdate, namaUpdate);

        // calling a method to create a post and passing our modal class.
        Call<DataModalUpdateProfil> call = RetroServer.getRetrofitAPI().createPostUpdate(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModalUpdateProfil>() {
            @Override
            public void onResponse(Call<DataModalUpdateProfil> call, Response<DataModalUpdateProfil> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataModalUpdateProfil responseFromAPI = response.body();

                    // Assuming there is a "status" field in the response indicating success
                    if (responseFromAPI.getStatus().equals("success")) {
                        Toast.makeText(activity_updateprofil.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_updateprofil.this, MainActivity.class);
                        startActivity(intent);
                        finish(); //
                    } else {
                        Toast.makeText(activity_updateprofil.this, "Gagal Mengubah", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response (HTTP error, network issues, etc.)
                    Toast.makeText(activity_updateprofil.this, "Gagal Total", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataModalUpdateProfil> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}