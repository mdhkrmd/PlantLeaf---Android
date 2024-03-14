package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project167.Helper.ManagmentCart;
import com.example.project167.R;
import com.example.project167.databinding.ActivityDetailBinding;
import com.example.project167.domain.PopularDomain;

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