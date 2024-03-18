package com.example.project167.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project167.Activity.riwayatData;
import com.example.project167.R;

import java.util.List;

public class riwayatAdapter extends RecyclerView.Adapter<riwayatAdapter.riwayatViewHolder> {
    private Context context;
    private List<riwayatData> riwayatList;

    public riwayatAdapter(Context context, List<riwayatData> riwayatList) {
        this.context = context;
        this.riwayatList = riwayatList;
    }

    @NonNull
    @Override
    public riwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_riwayat_item, parent, false);
        return new riwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull riwayatViewHolder holder, int position) {
        riwayatData riwayatData = riwayatList.get(position);
        Glide.with(context)
                .load(riwayatList.get(position).getGambarRiwayat())
                .into(holder.pic);
        holder.txtId.setText(riwayatData.getIdRiwayat());
        holder.txtTanggal.setText(riwayatData.getTanggalRiwayat());
        holder.txtPenyakit.setText(riwayatData.getPenyakitRiwayat());
    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }

    static class riwayatViewHolder extends RecyclerView.ViewHolder {
        TextView txtTanggal, txtId, txtPenyakit;
        private ImageView pic;

        riwayatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.txtTanggal);
            txtId = itemView.findViewById(R.id.txtId);
            txtPenyakit = itemView.findViewById(R.id.txtPenyakit);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
