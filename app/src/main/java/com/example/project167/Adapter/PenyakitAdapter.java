package com.example.project167.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project167.Activity.TanamanData;
import com.example.project167.Activity.activity_detailpenyakit;
import com.example.project167.Activity.activity_detailtanaman;
import com.example.project167.Activity.penyakitData;
import com.example.project167.R;

import java.util.List;

public class PenyakitAdapter extends RecyclerView.Adapter<PenyakitAdapter.Viewholder>{
    private Context context;
    private List<penyakitData> penyakitDataList;

    public PenyakitAdapter(Context context, List<penyakitData> penyakitDataList) {
        this.context = context;
        this.penyakitDataList = penyakitDataList;
    }

    @NonNull
    @Override
    public PenyakitAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_penyakit_item, parent, false);
        context = parent.getContext();
        return new PenyakitAdapter.Viewholder(inflator);
    }

    @Override
    public void onBindViewHolder(@NonNull PenyakitAdapter.Viewholder holder, int position) {
        penyakitData PenyakitData = penyakitDataList.get(position);
        Glide.with(context)
                .load(penyakitDataList.get(position).getGambar_penyakit())
                .into(holder.pic);

        holder.title.setText(PenyakitData.getLabel_penyakit());

//        Buat nanti kalo gambar di klik -> Buat detail
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_detailpenyakit.class);

                Bundle bundle = new Bundle();
                bundle.putString("label_penyakit" , PenyakitData.getLabel_penyakit());
                bundle.putString("tentang_penyakit" , PenyakitData.getTentang_penyakit());
                bundle.putString("gejala" , PenyakitData.getGejala_penyakit());
                bundle.putString("gambar" , PenyakitData.getGambar_penyakit());
                bundle.putString("penanganan" , PenyakitData.getPenanganan());

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return penyakitDataList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
