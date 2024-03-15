package com.example.project167.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project167.Activity.TanamanData;
import com.example.project167.R;

import java.util.List;

public class TanamanAdapter extends RecyclerView.Adapter<TanamanAdapter.Viewholder>{
    Context context;
    List<TanamanData> tanamanList;

    public TanamanAdapter(Context context, List<TanamanData> tanamanList) {
        this.context = context;
        this.tanamanList = tanamanList;
    }

    @NonNull
    @Override
    public TanamanAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tanaman, parent, false);
        context = parent.getContext();
        return new TanamanAdapter.Viewholder(inflator);
    }

    @Override
    public void onBindViewHolder(@NonNull TanamanAdapter.Viewholder holder, int position) {
        TanamanData tanamanData = tanamanList.get(position);
        Glide.with(context)
                .load(tanamanList.get(position).getgambar())
                .into(holder.pic);

        holder.title.setText(tanamanData.getnama_tanaman());

//        Buat nanti kalo gambar di klik -> Buat detail
//        holder.pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String linkArtikel = tanamanData.getLinkArtikel();
//                Uri webpage = Uri.parse(linkArtikel);
//                Intent i = new Intent(Intent.ACTION_VIEW, webpage);
//                context.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return tanamanList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
