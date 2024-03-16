package com.example.project167.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.project167.R;

public class activity_detailtanaman extends AppCompatActivity {
    private ImageView itemPic;
    private TextView txtNama, txtTentang, txtMerawat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailtanaman);

        itemPic = findViewById(R.id.itemPic);
        txtNama = findViewById(R.id.txtNama);
        txtTentang = findViewById(R.id.txtTentang);
        txtMerawat = findViewById(R.id.txtMerawat);

        Bundle bundle = getIntent().getExtras();

        String foto = bundle.getString("gambar");
        String nama = bundle.getString("nama");
        String alamat = bundle.getString("tentang");
        String deskripsi = bundle.getString("merawat");

        Glide.with(this).load(foto).into(itemPic);
        txtNama.setText(nama);
        txtTentang.setText(alamat);
        txtMerawat.setText(deskripsi);
    }
}