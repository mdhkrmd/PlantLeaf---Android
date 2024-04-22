package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project167.R;

public class artikel_wv extends AppCompatActivity {

    WebView wv_artikel;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel_wv);

        wv_artikel = findViewById(R.id.wv_artikel);

        setStatusBarColor(artikel_wv.this);

        // Get the URL passed from x.java
        String urlDiterima = getIntent().getStringExtra("EXTRA_URL");

        // Set the web view client and load the received URL
        wv_artikel.getWebViewClient();
        wv_artikel.loadUrl(urlDiterima);

        // Enable JavaScript and DOM storage
        WebSettings webSettings = wv_artikel.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
    }

}