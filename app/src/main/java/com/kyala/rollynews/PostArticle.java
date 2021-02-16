package com.kyala.rollynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kyala.rollynews.model.Info;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class PostArticle extends AppCompatActivity {

    FirebaseAuth mAuth;

    Uri file;

    String nameimage = "";
    Bitmap bitmap;
    ImageView image;
    private String nomfile = "";

    Info info = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_article);

        final EditText titre = (EditText)findViewById(R.id.titre);
        final EditText message = (EditText)findViewById(R.id.message);
        Button envoyer = (Button)findViewById(R.id.envoyer);

        mAuth = FirebaseAuth.getInstance();

        image = (ImageView)findViewById(R.id.image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        final FirebaseUser currentUser = mAuth.getCurrentUser();
        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = message.getText().toString();
                String title = titre.getText().toString();

                String iduser = currentUser.getUid();
                if (msg.isEmpty()){
                    message.setError("Ce champ est obligatoire");
                }else if (title.isEmpty()){
                    titre.setError("Ce champ est obligatoire");
                }else {
                    info = new Info("Info" + new Date().toString()+new Random(1000).nextInt(), title, msg, nameimage);

                    Log.i("titre", "titre");
                    Log.i("inforecup", "info : "+info.getTitre());
                    Envoyer(info);
                }
            }
        });
    }

    public void Envoyer(Info info){
        if (file != null){
            uploadFile();
        }else{
            StoreData(info);
            Log.i("infost", info.getTitre());
        }

    }
    public void StoreData(Info info){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://rolly-news-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("informations");

        Log.i("info", info.getTitre());

        myRef.child(info.getId()).setValue(info) ;

        Toast.makeText(this.getApplicationContext(), "Message envoyé !", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void uploadFile(){
        StorageReference storageRef;
        storageRef = FirebaseStorage.getInstance("gs://rolly-news.appspot.com").getReference();
        //file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        final StorageReference riversRef = storageRef.child("images/"+nameimage);

        riversRef.putFile(file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                String image = Objects.requireNonNull(task.getResult()).getStorage().getDownloadUrl().toString();
                Log.i("imagel", image);
            }
        })
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                String downloadUrl = taskSnapshot.getStorage().getPath();
                Log.i("imagel", downloadUrl);
                // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(PostArticle.this, "Image Postée", Toast.LENGTH_SHORT).show();
                file = null;
                info.setImage(downloadUrl);
                StoreData(info);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(PostArticle.this, "Upload de l'image échoué", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Selectionnez une image"), 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
           file = data.getData();
            image.setImageURI(file);
        } else if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            file = data.getData();
            nameimage = String.valueOf(System.currentTimeMillis()) + ".jpg";
            image.setImageURI(file);
        }
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        nomfile = Base64.encodeToString(b, Base64.DEFAULT);
        return nomfile;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}