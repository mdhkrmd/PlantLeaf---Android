package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.Datamodal.DataModalUpdateCatatan;
import com.example.project167.Datamodal.DataModalUpdateProfil;
import com.example.project167.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView classTxt, confTxt, txtTentang, txtGejala, txtPenanganan, txt_id_pred;
    ImageView resultPic, backBtn;
    FloatingActionButton noteBtn;
    EditText inputCatatan;
    Button btnCatat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);

            classTxt = findViewById(R.id.classTxt);
            confTxt = findViewById(R.id.confTxt);
            txtTentang = findViewById(R.id.txtTentang);
            txtGejala = findViewById(R.id.txtGejala);
            txtPenanganan = findViewById(R.id.txtPenanganan);
            resultPic = findViewById(R.id.itemPic);
            backBtn = findViewById(R.id.backBtn);
            noteBtn = findViewById(R.id.noteBtn);
            txt_id_pred = findViewById(R.id.id_pred);
            inputCatatan = findViewById(R.id.inputCatatan);
            btnCatat = findViewById(R.id.btnCatat);

            setStatusBarColor(DetailActivity.this);

            noteBtn.setColorFilter(getResources().getColor(R.color.white));

            // Get the Intent that started this activity
            Intent intent = getIntent();
            if (intent != null) {
                // Retrieve the data from the Intent
                String label = intent.getStringExtra("label");
                String sublabel = intent.getStringExtra("sublabel");
                String tentang = intent.getStringExtra("tentang");
                String gejala = intent.getStringExtra("gejala");
                String penanganan = intent.getStringExtra("penanganan");
                String base64Image = intent.getStringExtra("gambar");
                String id_pred = intent.getStringExtra("id_pred");

                // Assuming you have the Base64 encoded image string
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                resultPic.setImageBitmap(decodedByte);

                // Set the text of your TextViews
                confTxt.setText(label);
                classTxt.setText(sublabel);
                txtTentang.setText(tentang);
                txtGejala.setText(gejala);
                txtPenanganan.setText(penanganan);
                txt_id_pred.setText(id_pred);
            }

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            noteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetailActivity.this, "Tambah Catatan", Toast.LENGTH_SHORT).show();
                    inputCatatan.setEnabled(true);
                    btnCatat.setEnabled(true);
                }
            });

        btnCatat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (txt_id_pred.getText().toString().trim().isEmpty()
                        || inputCatatan.getText().toString().trim().isEmpty()) {
                    Toast.makeText(DetailActivity.this, "Mohon lengkapi yang masih kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    postDataUpdateCatatan(txt_id_pred.getText().toString(),
                            inputCatatan.getText().toString());
                }
            }
        });
    }
    private void postDataUpdateCatatan(String id_catatan, String catatan) {

        // passing data from our text fields to our modal class.
        DataModalUpdateCatatan modal = new DataModalUpdateCatatan(id_catatan, catatan);

        // calling a method to create a post and passing our modal class.
        Call<DataModalUpdateCatatan> call = RetroServer.getRetrofitAPI().createPostUpdateCatatan(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModalUpdateCatatan>() {
            @Override
            public void onResponse(Call<DataModalUpdateCatatan> call, Response<DataModalUpdateCatatan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataModalUpdateCatatan responseFromAPI = response.body();

                    // Assuming there is a "status" field in the response indicating success
                    if (responseFromAPI.getStatus().equals("success")) {
                        Toast.makeText(DetailActivity.this, "Berhasil Menambah Catatan", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); //
                    } else {
                        Toast.makeText(DetailActivity.this, "Gagal Menambah Catatan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response (HTTP error, network issues, etc.)
                    Toast.makeText(DetailActivity.this, "Gagal Total", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DataModalUpdateCatatan> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
//                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}