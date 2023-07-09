package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class QuestionBank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bank);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());
    }
}