package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project167.R;

public class panduan extends AppCompatActivity {

    ImageView backBtn, fotoPanduan;
    TextView txt1, txt2, txt3;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);

        backBtn = findViewById(R.id.backBtn);
        fotoPanduan = findViewById(R.id.itemPic);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);

        setStatusBarColor(panduan.this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt1.setText("Pastikan Anda mendekatkan kamera hingga jarak yang optimal dari daun tanaman " +
                "yang akan difoto. Jarak yang dekat memungkinkan detil dan tekstur daun terlihat " +
                "lebih jelas, sehingga memudahkan dalam proses deteksi penyakit. " +
                "Usahakan untuk tidak terlalu dekat sehingga daun menjadi blur, atau " +
                "terlalu jauh sehingga detailnya tidak terlihat");
        txt2.setText("Untuk hasil yang terbaik, fokuskan pengambilan gambar pada satu daun per foto. " +
                "Hindari memasukkan banyak daun dalam satu frame karena hal ini dapat menyulitkan aplikasi " +
                "dalam menganalisa kondisi daun secara akurat.");
        txt3.setText("Pencahayaan yang baik adalah kunci untuk mendapatkan foto yang jelas dan terang. " +
                "Ambillah foto di bawah sinar matahari langsung atau di dalam ruangan dengan pencahayaan " +
                "yang baik. ");
    };
    }