package com.sggs.sggs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sggs.sggs.adapters.EditSubjectAdapter;
import com.sggs.sggs.adapters.SelectSubjectAdapter;
import com.sggs.sggs.adapters.SubjectAdapter;
import com.sggs.sggs.loadingAnimation.LoadingDialog;
import com.sggs.sggs.model.SubjectModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class EditCourse extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView tvYear, tvBranch, tvAcademicYear, tvDiv;
    public static List<String> subjectList = new ArrayList<>();;
    LoadingDialog loadingDialog;
    RecyclerView rcShowSubject;
    List<String> subjectListSpinner = new ArrayList<>();
    Button btnAddSub, btnNewCourse;
    SpinnerDialog spinnerDialog;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        loadingDialog  = new LoadingDialog(this);
        loadingDialog.startLoading();

        rcShowSubject = findViewById(R.id.rcShowSubject);

        btnAddSub = findViewById(R.id.btnAddSubject);
        btnNewCourse = findViewById(R.id.btnNewCourse);

        tvBranch = findViewById(R.id.branchSelector);
        tvDiv = findViewById(R.id.divisionSelector);
        tvYear = findViewById(R.id.yearSelector);
        tvAcademicYear = findViewById(R.id.academicYearSelector);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);

        tvYear.setText(sharedPreferences.getString("year",""));
        tvBranch.setText(sharedPreferences.getString("branch",""));
        tvDiv.setText(sharedPreferences.getString("division",""));
        tvAcademicYear.setText(sharedPreferences.getString("academicYear",""));

        String[] subjectstring = getResources().getStringArray(R.array.subjectList);
        subjectListSpinner = new ArrayList<>(Arrays.asList(subjectstring));

        spinnerDialog=new SpinnerDialog((Activity) EditCourse.this, (ArrayList<String>) subjectListSpinner,"Select or Search Subject","Close");// With No Animation
        spinnerDialog=new SpinnerDialog((Activity) EditCourse.this, (ArrayList<String>) subjectListSpinner,"Select or Search Subject",R.style.DialogAnimations_SmileWindow,"Close");// With 	Animation

        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default


        spinnerDialog.bindOnSpinerListener((item, position) -> {
            Toast.makeText(EditCourse.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
            addDocumentToCollection(item);
        });

        btnAddSub.setOnClickListener(v->showConfirmationDialog());

        btnNewCourse.setOnClickListener(v->{
            showConfirmationDialogToAddNewCourse();
        });
        loadSubject();
    }

    private void setSubjectAdapter(){

        // Example in an Activity
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcShowSubject.setLayoutManager(layoutManager);

        EditSubjectAdapter adapter = new EditSubjectAdapter(
                sharedPreferences.getString("academicYear",""),
                sharedPreferences.getString("division","") ,
                sharedPreferences.getString("regNum",""),
                subjectList,
                this);
        rcShowSubject.setAdapter(adapter);

    }

    private void loadSubject(){
        subjectList.clear();
        try {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Students")
//                    .document(sharedPreferences.getString("branch",""))
//                    .collection(sharedPreferences.getString("year",""))
//                    .document(sharedPreferences.getString("division",""))
//                    .collection(sharedPreferences.getString("regNum",""))
                    .document(sharedPreferences.getString("regNum",""))


                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> documentData = documentSnapshot.getData();

                            // Access the data in the document
                            for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue().toString();
                                int v = Integer.parseInt(value);
                                subjectList.add(key);

                            }
                            setSubjectAdapter();
                            loadingDialog.stopLoading();
                        } else {
                            loadingDialog.stopLoading();
                        }

                    })
                    .addOnFailureListener(e -> {
                        loadingDialog.stopLoading();
                        Toast.makeText(EditCourse.this, "Error getting document" + e, Toast.LENGTH_SHORT).show();
                    });

        }catch (Exception ignored){
            loadingDialog.stopLoading();
        }

    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning!!!");
        builder.setMessage("Adding the wrong subject can have a negative impact on marks.\nOnly include subjects that you have in this semester.");

        builder.setPositiveButton("Ok", (dialog, which) -> {
            spinnerDialog.showSpinerDialog();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Dismiss the dialog
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addDocumentToCollection(String subject) {

        String branch = "common";

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        if(sharedPreferences.getString("year","").equals("FY")){
            branch = "common";
        }else {
            branch = sharedPreferences.getString("branch","");
        }

        DocumentReference documentReference = firestore.collection("Teachers")
                .document(sharedPreferences.getString("academicYear",""))
                .collection(subject)
                .document(branch)
                .collection(sharedPreferences.getString("division",""))
                .document(sharedPreferences.getString("regNum",""));

// Create a HashMap with the field-value pair you want to update
        Map<String, Object> data = new HashMap<>();
        data.put("test", "test");

        documentReference.set(data).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
            sendDataToFireStore(subject);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });


    }

    private void sendDataToFireStore(String subject){

        Map<String, Object> data = new HashMap<>();

        data.put(subject, 0);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DocumentReference docRef = firestore.collection("Students").
                document(sharedPreferences.getString("regNum",""));

        docRef.update(data).addOnSuccessListener(aVoid -> {
            loadSubject();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Update to failed: Add course first ", Toast.LENGTH_SHORT).show();
        });
    }

    private void showConfirmationDialogToAddNewCourse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning!!!");
        builder.setMessage("By doing this current course will delete for permanent\nDo you want to do that?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(EditCourse.this, CourseSelecter.class);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            // Dismiss the dialog
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}