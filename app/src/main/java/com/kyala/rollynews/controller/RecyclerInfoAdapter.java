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
import com.kyala.rollynews.model.Info;
import com.kyala.rollynews.R;

public class RecyclerInfoAdapter extends FirebaseRecyclerAdapter<Info, RecyclerInfoAdapter.myViewHolder> {
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecyclerInfoAdapter(@NonNull FirebaseRecyclerOptions<Info> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull final Info info) {
        myViewHolder.titre.setText(info.getTitre());
        myViewHolder.info.setText(info.getMessage());
        Glide.with(context).load(info.getImage()).centerCrop().into(myViewHolder.image);

        final String titre = info.getTitre();
        final String message = info.getMessage();
        final String image = info.getImage();

        myViewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Article.class);
                intent.putExtra("titre", titre);
                intent.putExtra("message", message);
                intent.putExtra("image", image);
                intent.putExtra("idarticle", info.getId());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        context = parent.getContext();
        myViewHolder holder = new myViewHolder(v);
        return holder;
    }
    class  myViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView titre, info;
        LinearLayout item;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image);
            titre = (TextView)itemView.findViewById(R.id.titre);
            info = (TextView)itemView.findViewById(R.id.info);
            item = (LinearLayout) itemView.findViewById(R.id.item);
        }
    }
}
