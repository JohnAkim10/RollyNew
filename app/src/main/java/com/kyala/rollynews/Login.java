package com.kyala.rollynews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kyala.rollynews.model.User;

public class Login extends AppCompatActivity {

    ProgressDialog dialog;
    EditText Email, Mdp;
    Button connexion;
    TextView inscrire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);

        Email = (EditText)findViewById(R.id.email);
        Mdp = (EditText)findViewById(R.id.mdp);

        connexion = findViewById(R.id.connexion);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Email.getText().toString().isEmpty() && !Mdp.getText().toString().isEmpty()) {
                    dialog.setContentView(R.layout.dialog);
                    dialog.setTitle("Chargement");
                    dialog.setMessage("Connexion à la base de donnée");
                    dialog.show();
                    Connexion(Email.getText().toString(), Mdp.getText().toString());
                }else{
                    Toast.makeText(Login.this, "Veuillez compléter tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
        inscrire = findViewById(R.id.inscrire);
        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Inscription.class);
                startActivity(intent);
            }
        });
    }

    public void Connexion(String email, String password){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(Task task) {
                        if (task.isSuccessful()) {
                            Log.d("result", "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();

                            creerSession(user.getUid(), user.getEmail(), user.getDisplayName());

                            Intent intent = new Intent(Login.this, Accueil.class);
                            intent.putExtra("email", user.getEmail());
                            intent.putExtra("id", user.getUid().toString());
                            startActivity(intent);
                        } else {
                            Log.w("result", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
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

        Log.d("session", "Création de la session: "+editor.toString());
        editor.apply(); // commit changes
    }

//    public boolean CreateSession(User user) {
//        SharedPreferences pref = context.getSharedPreferences("Session", 0); // 0 - for private mode
//        SharedPreferences.Editor editor = pref.edit();
//
//        editor.putString("id", user.getNom());
//        editor.putString("noms", user.getContact());
//
//        return editor.commit();
//    }

    public void SupprimerSession(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Session", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.clear();
        editor.commit();
    }



}