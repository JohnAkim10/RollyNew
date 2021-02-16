package com.kyala.rollynews.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.kyala.rollynews.Article;
import com.kyala.rollynews.R;
import com.kyala.rollynews.model.Info;

import java.util.ArrayList;

public class RecyclerImagesAdapter extends RecyclerView.Adapter<RecyclerImagesAdapter.myViewHolder> {
    Context context;
    ArrayList<Info> informations;

    /**
     *
     * @param context
     * @param informations
     */
    public RecyclerImagesAdapter(Context context, ArrayList<Info> informations) {
        this.context = context;
        this.informations = informations;
    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);
        context = parent.getContext();
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Info info = informations.get(position);
        Glide.with(context).load(info.getImage()).centerCrop().into(holder.image);

        final String titre = info.getTitre();
        final String message = info.getMessage();
        final String image = info.getImage();

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Article.class);
                intent.putExtra("titre", titre);
                intent.putExtra("message", message);
                intent.putExtra("image", image);
                context.startActivity(intent);
            }
        });
    }

    static class  myViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView titre, info;
        LinearLayout item;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            titre = itemView.findViewById(R.id.titre);
            info = itemView.findViewById(R.id.info);
            item =  itemView.findViewById(R.id.item);
        }
    }
}
