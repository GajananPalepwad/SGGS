package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sggs.sggs.loadingAnimation.LoadingDialog;

import org.checkerframework.checker.units.qual.A;

public class AcademicCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calendar);

        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        ImageView cal = findViewById(R.id.calendar);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        String data = sharedPreferences.getString("year","");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1 = ref.
                child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").
                child("SingalLinks").
                child("AcadamicCalendar"+data).child("Url");
        Sheet1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String name=snapshot.getValue(String.class);
                    Glide.with(AcademicCalendar.this).load(name).into(cal);
                }
                else {
                    Toast.makeText(AcademicCalendar.this, "PLEASE WAIT FOR ACADEMIC CALENDAR", Toast.LENGTH_LONG).show();
                }
                loadingDialog.stopLoading();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}