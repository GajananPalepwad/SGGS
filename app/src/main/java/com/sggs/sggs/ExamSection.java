package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;


public class ExamSection extends AppCompatActivity {

    CardView admitCard, timetable, result, gpaCalci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_section);

        admitCard = findViewById(R.id.admitCard);
        timetable = findViewById(R.id.timetable);
        result = findViewById(R.id.result);
        gpaCalci = findViewById(R.id.gpaCalculator);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        admitCard.setOnClickListener(v -> {
            Intent intent = new Intent(ExamSection.this, AdmitCard.class);
            startActivity(intent);
        });

        timetable.setOnClickListener(v -> {
            Intent intent = new Intent(ExamSection.this, ExamTimeTable.class);
            startActivity(intent);
        });

        result.setOnClickListener(v -> {
            Intent intent = new Intent(ExamSection.this,StudentResult.class);
            startActivity(intent);
        });

        gpaCalci.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://vishalibitwar.github.io/gpacalculator/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

    }
}