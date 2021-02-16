package com.kyala.rollynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kyala.rollynews.controller.RecyclerCommentaireAdapter;
import com.kyala.rollynews.controller.RecyclerInfoAdapter;
import com.kyala.rollynews.model.Commentaire;
import com.kyala.rollynews.model.Info;
import com.kyala.rollynews.model.Message;
import com.kyala.rollynews.model.User;

import java.util.Date;

public class Article extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView Titre, Message;

    ImageView Image;
    RecyclerView recyclerView;
    RecyclerCommentaireAdapter adapter;

    User user;
    String iduser;
    FirebaseUser currentUser;
    String idarticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        final String titre = intent.getStringExtra("titre");
        final String message = intent.getStringExtra("message");
        final String image = intent.getStringExtra("image");
        idarticle = intent.getStringExtra("idarticle");

        Log.i("article", "idartcle : "+idarticle);

        Titre = findViewById(R.id.titre);
        Message = findViewById(R.id.message);
        Image = findViewById(R.id.image);

        if (!image.isEmpty()){
            Log.i("image", image);
            Glide.with(getApplicationContext())
                    .load(image)
                    .into(Image);
        }else{
            Log.i("image", image);
        }

        Titre.setText(titre);
        Message.setText(message);

        final EditText msg = findViewById(R.id.commentaire);
        FloatingActionButton envoyer = findViewById(R.id.envoyer);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        Log.i("currentuser", currentUser.getDisplayName());

        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msg.getText().toString();
                if (message.isEmpty()){
                    msg.setError("Ce champ est obligatoire");
                }else if (iduser.isEmpty()){
                    Toast.makeText(Article.this, "Vous n'etes pas authentifié", Toast.LENGTH_SHORT).show();
                }else {
                    Envoyer(new Commentaire(currentUser.getDisplayName(), message, new Date().toString(), idarticle));
                }
            }
        });

        FirebaseRecyclerOptions<Commentaire> options =
                new FirebaseRecyclerOptions.Builder<Commentaire>().
                        setQuery(FirebaseDatabase.getInstance("https://rolly-news-default-rtdb.firebaseio.com/").getReference().child("commentaires").orderByChild("idarticle").equalTo(idarticle), Commentaire.class).build();

        Log.i("options", "donnees comments : "+options.toString());

        adapter = new RecyclerCommentaireAdapter(options);

        recyclerView = findViewById(R.id.recycler_commentaires);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void Envoyer(Commentaire commentaire){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://rolly-news-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("commentaires");

        myRef.child(commentaire.getId()).setValue(commentaire) ;

        Toast.makeText(this.getApplicationContext(), "Message envoyé !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

        iduser = currentUser.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance("https://rolly-news-default-rtdb.firebaseio.com/").getReference("users").child(iduser);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Article.this, "Cet user n'existe pas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }
}