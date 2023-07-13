package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sggs.sggs.adapters.AdmitCardAdapter;
import com.sggs.sggs.model.AdmitCardModel;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

//This class is for admit card
public class AdmitCard extends AppCompatActivity {
    String data;

    TextView namev, classs, regv, examname, statusv;

    private AdmitCardAdapter adapter;


    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admit_card);

        recyclerView = findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        data = intent.getStringExtra("dataname");

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        examname=findViewById(R.id.examname);
        namev=findViewById(R.id.name);
        regv=findViewById(R.id.regno);
        statusv=findViewById(R.id.status);


        List<AdmitCardModel> dataList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc")
                .child("AdmitCard")
                .child("721842942");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey(); // Get the key
                    Object value = snapshot.getValue(); // Get the value
                    String values = String.valueOf(value);
                    dataList.add(new AdmitCardModel(key, values));
                }

                Collections.sort(dataList, new Comparator<AdmitCardModel>() {
                    @Override
                    public int compare(AdmitCardModel model1, AdmitCardModel model2) {
                        String key1 = model1.getKey();
                        String key2 = model2.getKey();
                        return key1.compareTo(key2);
                    }
                });


                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdmitCard.this));

                // Initialize and set the adapter
                adapter = new AdmitCardAdapter(dataList, AdmitCard.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });



        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dbpath = db.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc")
                .child("AdmitCard")
                .child("721842942");
        dbpath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();
                    examname.setText((String) data.get("examname"));
                    namev.setText("Name:- "+(String) data.get("name"));
                    regv.setText("Reg no:- "+(String) data.get("regno"));
                    statusv.setText("Status:- "+(String) data.get("status"));


                } else {
                    Toast.makeText(AdmitCard.this, "Admit Card not found", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}