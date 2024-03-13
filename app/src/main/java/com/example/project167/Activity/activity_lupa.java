package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project167.R;
import com.example.project167.Datamodal.DataModalForgot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_lupa extends AppCompatActivity {

    EditText inputUsername, inputNewpassword;
    Button btnForgot;
    TextView txtLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa);

        inputUsername = findViewById(R.id.inputUsername);
        inputNewpassword = findViewById(R.id.inputNewPassword);
        txtLogin = findViewById(R.id.txtLogin);
        btnForgot = findViewById(R.id.btnForgot);

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (inputUsername.getText().toString().trim().length()==0
                        || inputNewpassword.getText().toString().trim().length()==0) {
                    Toast.makeText(activity_lupa.this, "Mohon lengkapi yang masih kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    postDataForgot(inputUsername.getText().toString(),
                            inputNewpassword.getText().toString());
                }
            }
        });
    }
    private void postDataForgot(String username, String new_password) {

        // below line is for displaying our progress bar.
//        loadingPB.setVisibility(View.VISIBLE);

        // passing data from our text fields to our modal class.
        DataModalForgot modal = new DataModalForgot(username, new_password);

        // calling a method to create a post and passing our modal class.
        Call<DataModalForgot> call = RetroServer.getRetrofitAPI().createPostForgot(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModalForgot>() {
            @Override
            public void onResponse(Call<DataModalForgot> call, Response<DataModalForgot> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataModalForgot responseFromAPI = response.body();

                    // Assuming there is a "status" field in the response indicating success
                    if (responseFromAPI.getStatus().equals("success")) {
                        Toast.makeText(activity_lupa.this, "Password Berhasil Diubah", Toast.LENGTH_SHORT).show();
                        inputUsername.setText("");
                        inputNewpassword.setText("");

//                        Intent intent = new Intent(activity_lupa.this, login.class);
//                        startActivity(intent);
                        finish(); //
                    } else {
                        Toast.makeText(activity_lupa.this, "Gagal Login / Username Tidak Terdaftar", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response (HTTP error, network issues, etc.)
                    Toast.makeText(activity_lupa.this, "Gagal Login / Username Tidak Terdaftar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataModalForgot> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}