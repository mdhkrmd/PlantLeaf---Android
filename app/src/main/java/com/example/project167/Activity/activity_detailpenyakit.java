package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.project167.R;

public class activity_detailpenyakit extends AppCompatActivity {

    private ImageView itemPic, backBtn;
    private TextView txtNama, txtTentang, txtGejala, txtPenanganan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpenyakit);

        itemPic = findViewById(R.id.itemPic);
        txtNama = findViewById(R.id.txtNama);
        txtTentang = findViewById(R.id.txtTentang);
        txtGejala = findViewById(R.id.txtGejala);
        backBtn = findViewById(R.id.backBtn);
        txtPenanganan = findViewById(R.id.txtPenanganan);

        setStatusBarColor(activity_detailpenyakit.this);

        Bundle bundle = getIntent().getExtras();

        String foto = bundle.getString("gambar");
        String label = bundle.getString("label_penyakit");
        String tentang = bundle.getString("tentang_penyakit");
        String gejala = bundle.getString("gejala");
        String penanganan = bundle.getString("penanganan");

        Glide.with(this).load(foto).into(itemPic);
        txtNama.setText(label);
        txtTentang.setText(tentang);
        txtGejala.setText(gejala);
        txtPenanganan.setText(penanganan);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}