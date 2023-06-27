package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.List;
import java.util.Map;

public class home extends AppCompatActivity {

    Button scanner;
    TextView name, email;
    ImageView profile, notificationBtn;
    String personEmail;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    RecyclerView recyclerView;
    ArrayList<Subject> subjectList;
    SubjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        scanner = findViewById(R.id.scanner);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        profile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recyclerView);
        notificationBtn = findViewById(R.id.notificationBtn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            personEmail = acct.getEmail();
            String personImg = acct.getPhotoUrl().toString();
            name.setText(personName);
            email.setText(personEmail);
            Glide.with(this).load(personImg).into(profile);
        }

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Scanner.class);
                startActivity(intent);
            }
        });

        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, CourseSelecter.class);
                startActivity(intent);
            }
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
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
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
                            Toast.makeText(home.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e("FirestoreError", "Error getting document: " + e.getMessage());
                        Toast.makeText(home.this, "" + e, Toast.LENGTH_SHORT).show();
                    }
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
        Intent intent = new Intent(home.this, TimeTable.class);
        startActivity(intent);
    }
    public void web(View view){
        Uri uri = Uri.parse("https://swagdev.vercel.app/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void Result(View view){
        Intent intent = new Intent(home.this,ScannerForResult.class);
        startActivity(intent);
    }

    public void logout(View view){
        signOut();
    }
    public void Notice(View view){
        Intent intent = new Intent(home.this,NotificationSender.class);
        startActivity(intent);
    }

    public void aboutUs(View view){
        Intent intent = new Intent(home.this,AboutUs.class);
        startActivity(intent);
    }

    public void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(home.this, "SIGN OUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(home.this,GoogleLoginPage.class));
            }
        });

    }

    @Override
    public void onBackPressed() {}
}