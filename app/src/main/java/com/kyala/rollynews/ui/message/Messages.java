package com.kyala.rollynews.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kyala.rollynews.R;
import com.kyala.rollynews.model.Message;

public class Messages extends Fragment {

    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_messages, container, false);
        final EditText msg = (EditText)root.findViewById(R.id.message);
        Button envoyer = (Button)root.findViewById(R.id.envoyer);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msg.getText().toString();
                String iduser = currentUser.getUid();
                if (message.isEmpty()){
                    msg.setError("Ce champ est obligatoire");
                }else if (iduser.isEmpty()){
                    Toast.makeText(getContext(), "Vous n'etes pas authentifié", Toast.LENGTH_SHORT).show();
                }else {
                    Envoyer(new Message(iduser, message));
                }
            }
        });
        return root;
    }
    public void Envoyer(Message message){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://rolly-news-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("messages");

        myRef.child(message.getId()).setValue(message) ;

        Toast.makeText(this.getContext(), "Message envoyé !", Toast.LENGTH_SHORT).show();
    }
}