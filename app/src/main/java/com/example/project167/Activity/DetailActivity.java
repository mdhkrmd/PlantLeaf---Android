package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;

public class DetailActivity extends AppCompatActivity {

    TextView classTxt, confTxt, txtTentang, txtGejala, txtPenanganan;
    ImageView resultPic;

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

        setStatusBarColor(DetailActivity.this);

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
        }

    }

}