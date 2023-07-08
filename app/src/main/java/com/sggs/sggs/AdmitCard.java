package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//This class is for admit card
public class AdmitCard extends AppCompatActivity {
    String data;
    Button back;

    TextView namev, classs, regv, examname, statusv,sub1,sub2,sub3,sub4,sub5,sub6,sub7,sub8,sub9,sub10,sub11,sub12,sub13,sub14,sub15,sub16,sub17,sub18,sub19,sub20,sub21,sub22,sub23,sub24,sub25;

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        back=findViewById(R.id.back);
        progressBar = findViewById(R.id.prg);
        Intent intent = getIntent();
        data = intent.getStringExtra("dataname");


        String mobileNo="";
        int j=0;
        for(int i=0;i< data.length();i++){
            if(data.charAt(i)=='0'||
                    data.charAt(i)=='1'||
                    data.charAt(i)=='2'||
                    data.charAt(i)=='3'||
                    data.charAt(i)=='4'||
                    data.charAt(i)=='5'||
                    data.charAt(i)=='6'||
                    data.charAt(i)=='7'||
                    data.charAt(i)=='8'||
                    data.charAt(i)=='9'){
                mobileNo=mobileNo + data.charAt(i);
                j++;
            }
            if(j==10){
                break;
            }
        }

        examname=findViewById(R.id.examname);
        DatabaseReference re1f = FirebaseDatabase.getInstance().getReference();
        DatabaseReference She1et1 = re1f.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("examname");
        She1et1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String name=snapshot.getValue(String.class);
                    examname.setText(name);
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        namev=findViewById(R.id.name);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1 = ref.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("name");
        Sheet1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    progressBar.setVisibility(View.GONE);
                    String name=snapshot.getValue(String.class);
                    namev.setText("Name: " + name);
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        regv=findViewById(R.id.regno);
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet11 = ref1.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("regno");
        Sheet11.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String reg=snapshot.getValue(String.class);
                    regv.setText("Reg no: " + reg);
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        statusv=findViewById(R.id.status);
        DatabaseReference ref11 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet111 = ref11.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("status");
        Sheet111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s=snapshot.getValue(String.class);
                    statusv.setText("Status: " + s);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        classs=findViewById(R.id.classs);
        classs.setText("");


        sub1= findViewById(R.id.sub1);
        sub2= findViewById(R.id.sub2);
        sub3= findViewById(R.id.sub3);
        sub4= findViewById(R.id.sub4);
        sub5= findViewById(R.id.sub5);
        sub6= findViewById(R.id.sub6);
        sub7= findViewById(R.id.sub7);
        sub8= findViewById(R.id.sub8);
        sub9= findViewById(R.id.sub9);
        sub10= findViewById(R.id.sub10);
        sub11= findViewById(R.id.sub11);
        sub12= findViewById(R.id.sub12);
        sub13= findViewById(R.id.sub13);
        sub14= findViewById(R.id.sub14);
        sub15= findViewById(R.id.sub15);
        sub16= findViewById(R.id.sub16);
        sub17= findViewById(R.id.sub17);
        sub18= findViewById(R.id.sub18);
        sub19= findViewById(R.id.sub19);
        sub20= findViewById(R.id.sub20);
        sub21= findViewById(R.id.sub21);
        sub22= findViewById(R.id.sub22);
        sub23= findViewById(R.id.sub23);
        sub24= findViewById(R.id.sub24);
        sub25= findViewById(R.id.sub25);

        sub1.setVisibility(View.INVISIBLE);
        sub2.setVisibility(View.INVISIBLE);
        sub3.setVisibility(View.INVISIBLE);
        sub4.setVisibility(View.INVISIBLE);
        sub5.setVisibility(View.INVISIBLE);
        sub6.setVisibility(View.INVISIBLE);
        sub7.setVisibility(View.INVISIBLE);
        sub8.setVisibility(View.INVISIBLE);
        sub9.setVisibility(View.INVISIBLE);
        sub10.setVisibility(View.INVISIBLE);
        sub11.setVisibility(View.INVISIBLE);
        sub12.setVisibility(View.INVISIBLE);
        sub13.setVisibility(View.INVISIBLE);
        sub14.setVisibility(View.INVISIBLE);
        sub15.setVisibility(View.INVISIBLE);
        sub16.setVisibility(View.INVISIBLE);
        sub17.setVisibility(View.INVISIBLE);
        sub18.setVisibility(View.INVISIBLE);
        sub19.setVisibility(View.INVISIBLE);
        sub20.setVisibility(View.INVISIBLE);
        sub21.setVisibility(View.INVISIBLE);
        sub22.setVisibility(View.INVISIBLE);
        sub23.setVisibility(View.INVISIBLE);
        sub24.setVisibility(View.INVISIBLE);
        sub25.setVisibility(View.INVISIBLE);

        DatabaseReference ref111 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1111 = ref111.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub1");
        Sheet1111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub1.setVisibility(View.VISIBLE);
                        sub1.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref1111 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet11111 = ref1111.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub2");
        Sheet11111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub2.setVisibility(View.VISIBLE);
                        sub2.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref11111 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet111111 = ref11111.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub3");
        Sheet111111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub3.setVisibility(View.VISIBLE);
                        sub3.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref111111 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1111111 = ref111111.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub4");
        Sheet1111111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub4.setVisibility(View.VISIBLE);
                        sub4.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref1111111 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet11111111 = ref1111111.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub5");
        Sheet11111111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub5.setVisibility(View.VISIBLE);
                        sub5.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref11111111 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet111111111 = ref11111111.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub6");
        Sheet111111111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub6.setVisibility(View.VISIBLE);
                        sub6.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref111111111 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1111111111 = ref111111111.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub7");
        Sheet1111111111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub7.setVisibility(View.VISIBLE);
                        sub7.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref11112 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet111112 = ref11112.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub8");
        Sheet111112.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub8.setVisibility(View.VISIBLE);
                        sub8.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref111122 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1111122 = ref111122.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub9");
        Sheet1111122.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub9.setVisibility(View.VISIBLE);
                        sub9.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref1111222 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet11111222 = ref1111222.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub10");
        Sheet11111222.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub10.setVisibility(View.VISIBLE);
                        sub10.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref11112222 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet111112222 = ref11112222.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub11");
        Sheet111112222.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub11.setVisibility(View.VISIBLE);
                        sub11.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref11113 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet111113 = ref11113.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub12");
        Sheet111113.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub12.setVisibility(View.VISIBLE);
                        sub12.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref111133 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1111133 = ref111133.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub13");
        Sheet1111133.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub13.setVisibility(View.VISIBLE);
                        sub13.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref1111333 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet11111333 = ref1111333.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub14");
        Sheet11111333.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub14.setVisibility(View.VISIBLE);
                        sub14.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref11114 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet111114 = ref11114.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub15");
        Sheet111114.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub15.setVisibility(View.VISIBLE);
                        sub15.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref111144 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1111144 = ref111144.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub16");
        Sheet1111144.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub16.setVisibility(View.VISIBLE);
                        sub16.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref1111444 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet11111444 = ref1111444.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub17");
        Sheet11111444.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub17.setVisibility(View.VISIBLE);
                        sub17.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref11115 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet111115 = ref11115.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub18");
        Sheet111115.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub18.setVisibility(View.VISIBLE);
                        sub18.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref111155 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1111155 = ref111155.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub19");
        Sheet1111155.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub19.setVisibility(View.VISIBLE);
                        sub19.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref1111555 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet11111555 = ref1111555.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub20");
        Sheet11111555.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub20.setVisibility(View.VISIBLE);
                        sub20.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref11116 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet111116 = ref11116.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub21");
        Sheet111116.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub21.setVisibility(View.VISIBLE);
                        sub21.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref111166 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1111166 = ref111166.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub22");
        Sheet1111166.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub22.setVisibility(View.VISIBLE);
                        sub22.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref1111666 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet11111666 = ref1111666.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub23");
        Sheet11111666.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub23.setVisibility(View.VISIBLE);
                        sub23.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref11117 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet111117 = ref11117.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub24");
        Sheet111117.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub24.setVisibility(View.VISIBLE);
                        sub24.setText(s);
                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        DatabaseReference ref111177 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1111177 = ref111177.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("AdmitCard").child(mobileNo).child("sub25");
        Sheet1111177.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String s = snapshot.getValue(String.class);
                    if (!s.isEmpty()){
                        sub25.setVisibility(View.VISIBLE);
                        sub25.setText(s);
                    }
//                    if (sub25.getText().toString().isEmpty()) {
//                        sub25.setVisibility(View.INVISIBLE);
//                    }
                }
                else {
                    Toast.makeText(AdmitCard.this, "PLEASE WAIT FOR ADMIT CARD", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


}