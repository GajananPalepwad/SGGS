package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

//this is flash screen
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread() {

            public void run() {
                try {
                    sleep(2500);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    SharedPreferences sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);

                    if(sharedPreferences.getString("mobileNum","").isEmpty() ||
                            sharedPreferences.getString("regNum","").isEmpty() ||
                            sharedPreferences.getString("fullName","").isEmpty() ||
                            sharedPreferences.getString("email","").isEmpty() ||
                            sharedPreferences.getString("img","").isEmpty()) {

                        Intent intent = new Intent(MainActivity.this, LoginPage.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Intent intent = new Intent(MainActivity.this,Home.class);
                        startActivity(intent);
                        finish();
                    }



                }
            }
        };thread.start();
    }
}