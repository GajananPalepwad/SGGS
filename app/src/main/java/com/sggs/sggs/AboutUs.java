package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutUs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
       Button back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void insta(View view){
        Uri uri = Uri.parse("https://instagram.com/sggs_swag");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void linkdIn(View view){
        Uri uri = Uri.parse("https://www.linkedin.com/company/swag-developer-s-club/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void github(View view){
        Uri uri = Uri.parse("https://github.com/SWAGDevsClub");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void yuotube(View view){
        Uri uri = Uri.parse("https://m.youtube.com/@swagdevelopersclub/featured");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void feedback(View view){
        Uri uri = Uri.parse("https://forms.gle/fDDqepCVhLhHbEmc8");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void web(View view){
        Uri uri = Uri.parse("https://swagdev.vercel.app/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


}