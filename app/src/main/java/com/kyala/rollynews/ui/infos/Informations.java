package com.kyala.rollynews.ui.infos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kyala.rollynews.controller.RecyclerImagesAdapter;
import com.kyala.rollynews.controller.RecyclerInfoAdapter;
import com.kyala.rollynews.model.Info;
import com.kyala.rollynews.R;

import java.util.ArrayList;

public class Informations extends Fragment {
    RecyclerInfoAdapter adapter;
    RecyclerImagesAdapter adapter1;

    ArrayList<Info> informations;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        DatabaseReference ref = FirebaseDatabase.getInstance("https://rolly-news-default-rtdb.firebaseio.com/").getReference();


        FirebaseRecyclerOptions<Info> options =
                new FirebaseRecyclerOptions.Builder<Info>().
                        setQuery(ref.child("informations"), Info.class).build();

        Log.i("options", "donnees : "+options.toString());

        adapter = new RecyclerInfoAdapter(options);

        // adapter.startListening();

        RecyclerView recyclerView = root.findViewById(R.id.recycler_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setAdapter(adapter);

        final RecyclerView recyclerImages = root.findViewById(R.id.recycler_images);
        recyclerImages.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerImages.setAdapter(adapter1);

        DatabaseReference reference = FirebaseDatabase.getInstance("https://rolly-news-default-rtdb.firebaseio.com/").getReference();
        Query query = reference.child("informations");

        informations = new ArrayList<>();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("vale", dataSnapshot.toString());

                //informations = (ArrayList<Info>) dataSnapshot.getValue(ArrayList.class);

                adapter1 = new RecyclerImagesAdapter(getContext(), informations);

                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("erreurfire", "Failed to read value.", databaseError.toException());
            }
        });

//        FirebaseRecyclerOptions<Info> options1 =
//                new FirebaseRecyclerOptions.Builder<Info>().
//                        setQuery(FirebaseDatabase.getInstance("https://rolly-news-default-rtdb.firebaseio.com/").getReference().child("informations"), Info.class).build();
//
//        Log.i("options", "donnees : "+options1.toString());
//
//        adapter1 = new RecyclerImagesAdapter(options1);
//
//        recyclerView.setAdapter(adapter1);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}