package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;


public class ExamSection extends AppCompatActivity {

    CardView admitCard, timetable, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_section);

        admitCard = findViewById(R.id.admitCard);
        timetable = findViewById(R.id.timetable);
        result = findViewById(R.id.result);

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
            Intent intent = new Intent(ExamSection.this,ScannerForResult.class);
            startActivity(intent);
        });

    }
}