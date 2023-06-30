package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Profile extends AppCompatActivity {

    ImageView profile, back;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {

            SharedPreferences sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);
            SharedPreferences.Editor preferences = sharedPreferences.edit();

            preferences.putString("email","");
            preferences.putString("fullName","");
            preferences.putString("regNum","");
            preferences.putString("mobileNum","");
            preferences.putString("img","");
            preferences.putString("branch","");
            preferences.putString("year","");
            preferences.putString("division","");
            preferences.putString("semester","");
            preferences.apply();

            Intent intent = new Intent(Profile.this, GoogleLoginPage.class);
            startActivity(intent);
            finish();

        });


    }
}