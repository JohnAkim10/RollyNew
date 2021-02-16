package com.kyala.rollynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.kyala.rollynews.model.User;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (getSessionUser()==null){
                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(MainActivity.this, Accueil.class);
                    startActivity(i);
                }
                finish();
            }
        }, 3000);
    }

    public User getSessionUser(){
        SharedPreferences pref = MainActivity.this.getSharedPreferences("Session", 0); // 0 - for private mode
        //SharedPreferences.Editor editor = pref.edit();

        String id = pref.getString("id", "");
        String pseudo = pref.getString("noms", "");
        String email = pref.getString("email", "");

        Log.d("session", "session existante : "+ pref.toString());

        User user = null;

        if (id.isEmpty()){
            Log.d("sessionnull", "aucune session");
        }else{
            user = new User(id, pseudo, email, "");
        }
        //8404b81572471408061d889be06b9ee598d6b382
        return  user;
    }
}