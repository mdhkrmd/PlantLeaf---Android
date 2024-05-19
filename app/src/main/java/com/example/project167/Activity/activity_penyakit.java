package com.example.project167.Activity;

import static com.example.project167.Activity.MainActivity.setStatusBarColor;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project167.Adapter.PenyakitAdapter;
import com.example.project167.Adapter.TanamanAdapter;
import com.example.project167.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_penyakit extends AppCompatActivity {

    RecyclerView rvPenyakit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyakit);
        rvPenyakit = findViewById(R.id.rvPenyakit);

        getPenyakit();
        setStatusBarColor(activity_penyakit.this);
    }

    private void getPenyakit() {
        Call<List<penyakitData>> apiCall =  RetroServer.getRetrofitAPI().getPenyakit();
        apiCall.enqueue(new Callback<List<penyakitData>>() {
            @Override
            public void onResponse(Call<List<penyakitData>> call, Response<List<penyakitData>> response) {
                List<penyakitData> penyakitDataList = response.body();
                setAdapter2(penyakitDataList);
            }

            @Override
            public void onFailure(Call<List<penyakitData>> call, Throwable t) {
                Toast.makeText(activity_penyakit.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setAdapter2(List<penyakitData> penyakitDataList) {
        rvPenyakit.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        PenyakitAdapter penyakitAdapter = new PenyakitAdapter(this, penyakitDataList);
        rvPenyakit.setAdapter(penyakitAdapter);
    }
}