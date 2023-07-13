package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuestionBank extends AppCompatActivity {

    private FirebaseFirestore db;

    private String sem1e;
    private String sem1m;
    private String sem2e;
    private String sem2m;
    private String sem3e;
    private String sem3m;
    private String sem4e;
    private String sem4m;
    private String sem5e;
    private String sem5m;
    private String sem6e;
    private String sem6m;
    private String sem7e;
    private String sem7m;
    private String sem8e;
    private String sem8m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bank);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        db = FirebaseFirestore.getInstance();

        CardView sem1mCard = findViewById(R.id.sem1m);
        CardView sem1eCard = findViewById(R.id.sem1e);
        CardView sem2mCard = findViewById(R.id.sem2m);
        CardView sem2eCard = findViewById(R.id.sem2e);
        CardView sem3mCard = findViewById(R.id.sem3m);
        CardView sem3eCard = findViewById(R.id.sem3e);
        CardView sem4mCard = findViewById(R.id.sem4m);
        CardView sem4eCard = findViewById(R.id.sem4e);
        CardView sem5mCard = findViewById(R.id.sem5m);
        CardView sem5eCard = findViewById(R.id.sem5e);
        CardView sem6mCard = findViewById(R.id.sem6m);
        CardView sem6eCard = findViewById(R.id.sem6e);
        CardView sem7mCard = findViewById(R.id.sem7m);
        CardView sem7eCard = findViewById(R.id.sem7e);
        CardView sem8mCard = findViewById(R.id.sem8m);
        CardView sem8eCard = findViewById(R.id.sem8e);


        db.collection("QuestionBank").document("Link")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {

                            sem1e = document.getString("sem1e");
                            sem1m = document.getString("sem1m");
                            sem2e = document.getString("sem2e");
                            sem2m = document.getString("sem2m");
                            sem3e = document.getString("sem3e");
                            sem3m = document.getString("sem3m");
                            sem4e = document.getString("sem4e");
                            sem4m = document.getString("sem4m");
                            sem5e = document.getString("sem5e");
                            sem5m = document.getString("sem5m");
                            sem6e = document.getString("sem6e");
                            sem6m = document.getString("sem6m");
                            sem7e = document.getString("sem7e");
                            sem7m = document.getString("sem7m");
                            sem8e = document.getString("sem8e");
                            sem8m = document.getString("sem8m");

                        } else {
                            Toast.makeText(QuestionBank.this, "Dose not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(QuestionBank.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });


        sem1mCard.setOnClickListener(v -> openDriveFolder(sem1m));
        sem1eCard.setOnClickListener(v -> openDriveFolder(sem1e));
        sem2mCard.setOnClickListener(v -> openDriveFolder(sem2m));
        sem2eCard.setOnClickListener(v -> openDriveFolder(sem2e));
        sem3mCard.setOnClickListener(v -> openDriveFolder(sem3m));
        sem3eCard.setOnClickListener(v -> openDriveFolder(sem3e));
        sem4mCard.setOnClickListener(v -> openDriveFolder(sem4m));
        sem4eCard.setOnClickListener(v -> openDriveFolder(sem4e));
        sem5mCard.setOnClickListener(v -> openDriveFolder(sem5m));
        sem5eCard.setOnClickListener(v -> openDriveFolder(sem5e));
        sem6mCard.setOnClickListener(v -> openDriveFolder(sem6m));
        sem6eCard.setOnClickListener(v -> openDriveFolder(sem6e));
        sem7mCard.setOnClickListener(v -> openDriveFolder(sem7m));
        sem7eCard.setOnClickListener(v -> openDriveFolder(sem7e));
        sem8mCard.setOnClickListener(v -> openDriveFolder(sem8m));
        sem8eCard.setOnClickListener(v -> openDriveFolder(sem8e));



    }

    private void openDriveFolder(String driveLink) {
        try {
            Uri driveFolderUri = Uri.parse(driveLink);
            Intent intent = new Intent(Intent.ACTION_VIEW, driveFolderUri);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to open Google Drive folder", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}