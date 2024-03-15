package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;

public class DetailActivity extends AppCompatActivity {

    TextView classTxt, confTxt, txtTentang, txtGejala, txtPenanganan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        classTxt = findViewById(R.id.classTxt);
        confTxt = findViewById(R.id.confTxt);
        txtTentang = findViewById(R.id.txtTentang);
        txtGejala = findViewById(R.id.txtGejala);
        txtPenanganan = findViewById(R.id.txtPenanganan);

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


            // Set the text of your TextViews
            confTxt.setText(label);
            classTxt.setText(sublabel);
            txtTentang.setText(tentang);
            txtGejala.setText(gejala);
            txtPenanganan.setText(penanganan);
        }

    }

}