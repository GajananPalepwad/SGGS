package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentSender;
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
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class Home extends AppCompatActivity {

    Button scanner, addCourse;
    TextView name, tvReg;
    ImageView profile, notificationBtn;
    String personEmail;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    RecyclerView recyclerView;
    ArrayList<Subject> subjectList;
    SubjectAdapter adapter;
    private static final int MY_REQUEST_CODE = 100;

    CardView notes, examSection, timeTable, Events, calenarCard, qBank;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);
        preferences = sharedPreferences.edit();

        notes = findViewById(R.id.notes);

        scanner = findViewById(R.id.scanner);
        name = findViewById(R.id.name);
        tvReg = findViewById(R.id.tvReg);
        profile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recyclerView);
        notificationBtn = findViewById(R.id.notificationBtn);
        addCourse = findViewById(R.id.addCourseBtn);

        checkForAppUpdate();



        String personName = sharedPreferences.getString("fullName","");
        personEmail = sharedPreferences.getString("email","");
        name.setText(personName);
        tvReg.setText(sharedPreferences.getString("regNum",""));
        int selectedImageResource = getResources().getIdentifier(sharedPreferences.getString("img",""), "drawable", getPackageName());
        profile.setImageResource(selectedImageResource);


        profile.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Profile.class);
            startActivity(intent);
        });

        scanner.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Scanner.class);
            startActivity(intent);
        });

        addCourse.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, CourseSelecter.class);
            startActivity(intent);
        });

        notes.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, NotesSubjectList.class);
            intent.putExtra("subjectList", subjectList);
            startActivity(intent);
        });




        subjectList = new ArrayList<>();
        adapter = new SubjectAdapter(subjectList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        try {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Students")
                .document(sharedPreferences.getString("branch",""))
                .collection(sharedPreferences.getString("year",""))
                .document(sharedPreferences.getString("division",""))
                .collection(sharedPreferences.getString("regNum",""))
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
                        if(subjectList.size()!=0){
                            addCourse.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Home.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Home.this, "Error getting document" + e, Toast.LENGTH_SHORT).show();
                });
        }catch (Exception ignored){}

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new GridLayoutManager(home.this, 3));
        adapter = new SubjectAdapter(subjectList, this);
        recyclerView.setAdapter(adapter);

        int i = adapter.getItemCount();





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

    private void checkForAppUpdate(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // an activity result launcher registered via registerForActivityResult
                            AppUpdateType.IMMEDIATE,
                            // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                            this,
                            // flexible updates.
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    throw new RuntimeException(e);
                }
            }else{
              //  Toast.makeText(this, "NOT", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle callback
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, requestCode + "\n" + resultCode, Toast.LENGTH_SHORT).show();
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

}