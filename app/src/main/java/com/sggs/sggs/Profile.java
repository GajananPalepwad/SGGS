package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Profile extends AppCompatActivity {

    ImageView profile, back;
    Button logout;
    EditText name, email, reg, mobileNo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        reg = findViewById(R.id.regnum);
        mobileNo = findViewById(R.id.mobile_num);
        profile = findViewById(R.id.profile);

        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);
        preferences = sharedPreferences.edit();


        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {



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

        setAll();
    }

    public void web(View view){
        Uri uri = Uri.parse("https://swagdev.vercel.app/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void setAll(){

        name.setText(sharedPreferences.getString("fullName",""));
        mobileNo.setText(sharedPreferences.getString("mobileNum",""));
        email.setText(sharedPreferences.getString("email",""));
        reg.setText(sharedPreferences.getString("regNum",""));
        int selectedImageResource = getResources().getIdentifier(sharedPreferences.getString("img",""), "drawable", getPackageName());
        profile.setImageResource(selectedImageResource);

    }



}