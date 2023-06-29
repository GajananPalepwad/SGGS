package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class Home extends AppCompatActivity {

    Button scanner;
    TextView name, tvReg;
    ImageView profile, notificationBtn;
    String personEmail;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    RecyclerView recyclerView;
    ArrayList<Subject> subjectList;
    SubjectAdapter adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);
        scanner = findViewById(R.id.scanner);
        name = findViewById(R.id.name);
        tvReg = findViewById(R.id.tvReg);
        profile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recyclerView);
        notificationBtn = findViewById(R.id.notificationBtn);



        String personName = sharedPreferences.getString("fullName","");
        personEmail = sharedPreferences.getString("email","");
        String personImg = "";
        name.setText(personName);
        tvReg.setText(sharedPreferences.getString("regNum",""));
        int selectedImageResource = getResources().getIdentifier(sharedPreferences.getString("img",""), "drawable", getPackageName());
        profile.setImageResource(selectedImageResource);


        scanner.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Scanner.class);
            startActivity(intent);
        });

        notificationBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, CourseSelecter.class);
            startActivity(intent);
        });

        subjectList = new ArrayList<>();
        adapter = new SubjectAdapter(subjectList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Students")
                .document("CSE")
                .collection("SY")
                .document("A")
                .collection(personEmail)
                .document("Attendance")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> documentData = documentSnapshot.getData();

                        // Access the data in the document
                        for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue().toString();
                            int v = Integer.parseInt(value);
                            subjectList.add(new Subject(key, v));
                        }

                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Home.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Home.this, "Error getting document" + e, Toast.LENGTH_SHORT).show();
                });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new GridLayoutManager(home.this, 3));
        adapter = new SubjectAdapter(subjectList, this);
        recyclerView.setAdapter(adapter);







//////////////////////////////////////////////////////////////////////////////////////////////////////

    }



    public void timeTable(View view){
        Intent intent = new Intent(Home.this, TimeTable.class);
        startActivity(intent);
    }
    public void web(View view){
        Uri uri = Uri.parse("https://swagdev.vercel.app/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void Result(View view){
        Intent intent = new Intent(Home.this,ScannerForResult.class);
        startActivity(intent);
    }

    public void logout(View view){
        signOut();
    }
    public void Notice(View view){
        Intent intent = new Intent(Home.this,NotificationSender.class);
        startActivity(intent);
    }

    public void aboutUs(View view){
        Intent intent = new Intent(Home.this,AboutUs.class);
        startActivity(intent);
    }

    public void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Home.this, "SIGN OUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(Home.this,GoogleLoginPage.class));
            }
        });

    }

    @Override
    public void onBackPressed() {}
}