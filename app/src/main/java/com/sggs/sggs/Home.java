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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sggs.sggs.adapters.SubjectAdapter;
import com.sggs.sggs.loadingAnimation.LoadingDialog;
import com.sggs.sggs.model.SubjectModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Home extends AppCompatActivity {

    Button addCourse;
    TextView name, tvReg;
    ImageView profile, notificationBtn;
    String personEmail;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    RecyclerView recyclerView;
    ArrayList<SubjectModel> subjectList;
    SubjectAdapter adapter;
    private static final int MY_REQUEST_CODE = 100;
    LoadingDialog loadingDialog;
    CardView notes, examSection, timeTable, events, calendar, qBank;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadingDialog  = new LoadingDialog(this);
        loadingDialog.startLoading();


        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);
        preferences = sharedPreferences.edit();

        notes = findViewById(R.id.notes);
        name = findViewById(R.id.name);
        tvReg = findViewById(R.id.tvReg);
        profile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recyclerView);
        notificationBtn = findViewById(R.id.notificationBtn);
        addCourse = findViewById(R.id.addCourseBtn);
        timeTable = findViewById(R.id.classTime);
        examSection = findViewById(R.id.examSection);
        events = findViewById(R.id.events);
        calendar = findViewById(R.id.calendar);
        qBank = findViewById(R.id.Qbank);

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


        addCourse.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, CourseSelecter.class);
            startActivity(intent);
        });

        notes.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, NotesSubjectList.class);
            intent.putExtra("subjectList", subjectList);
            startActivity(intent);
        });

        timeTable.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, ClassTimeTable.class);
            startActivity(intent);
        });

        examSection.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, ExamSection.class);
            startActivity(intent);
        });

        notificationBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Notification.class);
            startActivity(intent);
        });

        events.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, EventPage.class);
            startActivity(intent);
        });

        calendar.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, AcademicCalendar.class);
            startActivity(intent);
        });

        qBank.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, QuestionBank.class);
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
                            subjectList.add(new SubjectModel(key, v));

                        }
                        if(subjectList.size()!=0){
                            addCourse.setVisibility(View.GONE);
                        }
                        loadingDialog.stopLoading();
                        adapter.notifyDataSetChanged();
                    } else {
                        loadingDialog.stopLoading();
                        Toast.makeText(Home.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }

                })
                .addOnFailureListener(e -> {
                    loadingDialog.stopLoading();
                    Toast.makeText(Home.this, "Error getting document" + e, Toast.LENGTH_SHORT).show();
                });

        }catch (Exception ignored){
            loadingDialog.stopLoading();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SubjectAdapter(subjectList, this);
        recyclerView.setAdapter(adapter);



    }


    public void web(View view){
        Uri uri = Uri.parse("https://swagdev.vercel.app/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    public void logout(View view){
        signOut();
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