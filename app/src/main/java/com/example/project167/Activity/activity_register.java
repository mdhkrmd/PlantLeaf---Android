package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project167.Datamodal.DataModalRegister;
import com.example.project167.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_register extends AppCompatActivity {

    EditText inputUsername,inputPassword, inputNik, inputNama;
    TextView tvLogin;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.editUsername);
        inputPassword = findViewById(R.id.editPassword);
        inputNik = findViewById(R.id.editNik);
        inputNama = findViewById(R.id.editNama);
        tvLogin = findViewById(R.id.tvLogin);
        btnPost = findViewById(R.id.idBtnPost);

        setStatusBarColor(activity_register.this);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (inputUsername.getText().toString().trim().length()==0
                        || inputPassword.getText().toString().trim().length()==0
                        || inputNik.getText().toString().trim().length()==0
                        || inputNama.getText().toString().trim().length()==0) {
                    Toast.makeText(activity_register.this, "Mohon lengkapi yang masih kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    postData(inputUsername.getText().toString(),
                            inputPassword.getText().toString(),
                            inputNik.getText().toString(),
                            inputNama.getText().toString());
                }
            }
        });
    }

    private void postData(String username, String password, String nik, String nama) {

        // below line is for displaying our progress bar.
//        loadingPB.setVisibility(View.VISIBLE);

        // passing data from our text fields to our modal class.
        DataModalRegister modal = new DataModalRegister(username, password, nik, nama);

        // calling a method to create a post and passing our modal class.
        Call<DataModalRegister> call = RetroServer.getRetrofitAPI().createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModalRegister>() {
            @Override
            public void onResponse(Call<DataModalRegister> call, Response<DataModalRegister> response) {
                // this method is called when we get response from our api.
                Toast.makeText(activity_register.this, "Berhasil daftar akun", Toast.LENGTH_SHORT).show();

                // below line is for hiding our progress bar.
//                loadingPB.setVisibility(View.GONE);

                // on below line we are setting empty text
                // to our both edit text.
                inputUsername.setText("");
                inputPassword.setText("");
                inputNik.setText("");
                inputNama.setText("");

//                Intent intent = new Intent(activity_register.this, login.class);
//                startActivity(intent);
                finish(); //

                // we are getting response from our body
                // and passing it to our modal class.
                DataModalRegister responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code();

                // below line we are setting our
                // string to our text view.
//                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModalRegister> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }

}