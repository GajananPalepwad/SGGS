package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesSubjectList extends AppCompatActivity {

    ArrayList<Subject> subjectList;
    private RecyclerView recyclerView;
    private NotesSubjectAdapter subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_subject_list);
        SharedPreferences sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);
        subjectList = (ArrayList<Subject>)getIntent().getSerializableExtra("subjectList");
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());


        recyclerView = findViewById(R.id.subList);
        recyclerView.setLayoutManager(new GridLayoutManager(NotesSubjectList.this, 2 ));

        // Get the ArrayList<Subject> from the intent or wherever you have it
        ArrayList<Subject> subjectList = getIntent().getParcelableArrayListExtra("subjectList");

        subjectAdapter = new NotesSubjectAdapter(
                subjectList,
                this,
                sharedPreferences.getString("branch",""),
                sharedPreferences.getString("year",""),
                sharedPreferences.getString("semester","")
        );
        recyclerView.setAdapter(subjectAdapter);

    }
}