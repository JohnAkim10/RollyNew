package com.kyala.rollynews.controller;

import android.content.Context;
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
import com.kyala.rollynews.R;
import com.kyala.rollynews.model.Commentaire;

public class RecyclerCommentaireAdapter extends FirebaseRecyclerAdapter<Commentaire, RecyclerCommentaireAdapter.myViewHolder> {
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecyclerCommentaireAdapter(@NonNull FirebaseRecyclerOptions<Commentaire> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull final Commentaire commentaire) {
        myViewHolder.titre.setText(commentaire.getAuteur());
        myViewHolder.info.setText(commentaire.getCommentaire());
        myViewHolder.date.setText(commentaire.getDate());
        Glide.with(context).load(commentaire.getIdarticle()).placeholder(R.drawable.rollyico).circleCrop().into(myViewHolder.image);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commentaire, parent, false);
        context = parent.getContext();
        myViewHolder holder = new myViewHolder(v);

        return holder;
    }
    class  myViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView titre, info, date;
        LinearLayout item;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image);
            titre = (TextView)itemView.findViewById(R.id.titre);
            info = (TextView)itemView.findViewById(R.id.info);
            date = (TextView)itemView.findViewById(R.id.date);
            item = (LinearLayout) itemView.findViewById(R.id.item);
        }
    }
}
