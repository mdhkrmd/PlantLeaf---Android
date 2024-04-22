package com.example.project167.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project167.R;

import java.util.List;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.Viewholder> {

    Context context;
    List<ArtikelData> artikelList;

    public ArtikelAdapter(Context context, List<ArtikelData> artikelList) {
        this.context = context;
        this.artikelList = artikelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pup_list, parent, false);
        context = parent.getContext();
        return new Viewholder(inflator);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ArtikelData artikelData = artikelList.get(position);
        Glide.with(context)
                .load(artikelList.get(position).getFotoArtikel())
                .into(holder.pic);

        holder.title.setText(artikelData.getJudulArtikel());
        holder.subtitle.setText(artikelData.getPenulisArtikel());

        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkArtikel = artikelData.getLinkArtikel();

                // Create an Intent to start y.java activity
                Intent i = new Intent(context, artikel_wv.class);
                i.putExtra("EXTRA_URL", linkArtikel); // Replace y_java_class_name with the actual class name of y.java

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return artikelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView title, subtitle;
        ImageView pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            subtitle = itemView.findViewById(R.id.feeTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
