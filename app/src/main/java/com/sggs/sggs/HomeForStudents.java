package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;

public class HomeForStudents extends AppCompatActivity {
    Button scanner;
    TextView name, email;
    ImageView profile;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_for_students);
        scanner = findViewById(R.id.scanner);
        Button result= findViewById(R.id.result);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        profile=findViewById(R.id.profile);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personImg = acct.getPhotoUrl().toString();
            name.setText(personName);
            email.setText(personEmail);
            Glide.with(this).load(personImg).into(profile);

        }

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeForStudents.this,Scanner.class);
                startActivity(intent);
            }
        });

    }

    public void timeTable(View view){
        Intent intent = new Intent(HomeForStudents.this, TimeTable.class);
        startActivity(intent);
    }
    public void web(View view){
        Uri uri = Uri.parse("https://swagdev.vercel.app/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void Result(View view){
        Intent intent = new Intent(HomeForStudents.this,ScannerForResult.class);
        startActivity(intent);
    }

    public void logout(View view){
       signOut();
    }

    public void aboutUs(View view){
        Intent intent = new Intent(HomeForStudents.this,AboutUs.class);
        startActivity(intent);
    }
    public void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(HomeForStudents.this, "SIGN OUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(HomeForStudents.this,GoogleLoginPage.class));
            }
        });

    }

    @Override
    public void onBackPressed() {}
}