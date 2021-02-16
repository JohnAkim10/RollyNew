package com.kyala.rollynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kyala.rollynews.model.User;

import java.io.ByteArrayOutputStream;

public class Inscription extends AppCompatActivity {

    ProgressDialog dialog;
    EditText Noms, Contact, Mdp, Confirm;
    Button inscrire;
    String nomfile="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        dialog = new ProgressDialog(this);

        Noms = findViewById(R.id.nom);
        Contact = findViewById(R.id.email);
        Mdp = findViewById(R.id.mdp);
        Confirm = findViewById(R.id.cmdp);

        inscrire = findViewById(R.id.inscrire);

        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Chargement");
                dialog.setMessage("Connexion à la base de donnée");
                dialog.show();
                if (Mdp.getText().toString().equals(Confirm.getText().toString())) {
                    Inscrire(new User(Noms.getText().toString(), Contact.getText().toString(), Mdp.getText().toString()));
                }else{
                    Toast.makeText(Inscription.this, "Les mots de passe ne correspondent pas !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private FirebaseAuth mAuth;
    public void Inscrire(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://rolly-news-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("users");

        myRef.child(user.getId()+"").setValue(user) ;

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(user.getContact(), user.getMdp())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("inscr", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            creerSession(user.getUid(), user.getEmail(), user.getDisplayName());

                            Intent intent = new Intent(Inscription.this, Accueil.class);
                            intent.putExtra("email", user.getEmail().toString());
                            intent.putExtra("name", user.getDisplayName().toString());
                            intent.putExtra("id", user.getUid().toString());
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("inscr", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Inscription.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                });
    }
    public void creerSession(String id, String email, String displayname){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Session", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("id", id); // Storing string
        editor.putString("email", email); // Storing boolean - true/false
        editor.putString("noms", displayname); // Storing boolean - true/false

        editor.commit(); // commit changes
    }
}