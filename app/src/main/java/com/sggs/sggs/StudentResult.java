package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentResult extends AppCompatActivity {

    TextView namev;
    TextView regv;
    TextView branchv;
    TextView sem;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    TextView t6;
    TextView t7;
    TextView t8;
    TextView p1;
    TextView p2;
    TextView p3;
    TextView p4;
    TextView p5;
    TextView p6;
    TextView p7;
    TextView p8;
    public ProgressBar progressBar;

    String name, reg, branch, data, semi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result);
        ImageView back=findViewById(R.id.back);
        progressBar = findViewById(R.id.prg);
        back.setOnClickListener(v -> onBackPressed());

        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        data = sharedPreferences.getString("regNum","");
        
        namev = findViewById(R.id.name);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1 = ref.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Name");
        Sheet1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                if(snapshot.exists()) {
                    name=snapshot.getValue(String.class);
                    namev.setText("Name: " + name);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        regv = findViewById(R.id.regno);
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet2 = ref1.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Reg_No");
        Sheet2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    reg=snapshot.getValue(String.class);
                    regv.setText("Reg no: " + reg);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        branchv = findViewById(R.id.classs);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet3 = ref2.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Branch");
        Sheet3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    branch=snapshot.getValue(String.class);
                    branchv.setText("Branch: " + branch);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        sem = findViewById(R.id.sem);
        DatabaseReference ref12 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet31 = ref12.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Sem");
        Sheet31.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    double d=snapshot.getValue(double.class);
                    String s = Double.toString(d);
                    sem.setText("Semister: " + s.charAt(0));
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        t6 = findViewById(R.id.t6);
        t7 = findViewById(R.id.t7);
        t8 = findViewById(R.id.t8);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);
        p4 = findViewById(R.id.p4);
        p5 = findViewById(R.id.p5);
        p6 = findViewById(R.id.p6);
        p7 = findViewById(R.id.p7);
        p8 = findViewById(R.id.p8);
        DatabaseReference ref112 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet311 = ref112.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject1");
        Sheet311.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    t1.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref1112 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet3111 = ref1112.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject2");
        Sheet3111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    t2.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref11112 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet31111 = ref11112.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject3");
        Sheet31111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    t3.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref111112 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet311111 = ref111112.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject4");
        Sheet311111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    t4.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref1111112 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet3111111 = ref1111112.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject5");
        Sheet3111111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    t5.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref11111112 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet31111111 = ref11111112.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject6");
        Sheet31111111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    t6.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref111111112 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet311111111 = ref111111112.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject7");
        Sheet311111111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    t7.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref1111111112 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet3111111111 = ref1111111112.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject8");
        Sheet3111111111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    t8.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref22 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet22 = ref22.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject1_Pract");
        Sheet22.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    p1.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref222 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet222 = ref222.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject2_Pract");
        Sheet222.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    p2.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref2222 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet2222 = ref2222.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject3_Pract");
        Sheet2222.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    p3.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref22222 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet22222 = ref22222.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject4_Pract");
        Sheet22222.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    p4.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref222222 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet222222 = ref222222.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject5_Pract");
        Sheet222222.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    p5.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref2222222 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet2222222 = ref2222222.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject6_Pract");
        Sheet2222222.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    p6.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref22222222 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet22222222 = ref22222222.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject7_Pract");
        Sheet22222222.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    p7.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref222222222 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet222222222 = ref222222222.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("Sheet1").child(data).child("Subject8_Pract");
        Sheet222222222.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    semi=snapshot.getValue(String.class);
                    p8.setText(semi);
                }
                else {
                    Toast.makeText(StudentResult.this, "PLEASE WAIT FOR RESULT", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void backlog(View view){
        Intent intent = new Intent(StudentResult.this, BackLogResult.class);
        startActivity(intent);
    }
}