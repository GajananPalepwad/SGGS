package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.sggs.sggs.adapters.SelectSubjectAdapter;
import com.sggs.sggs.loadingAnimation.LoadingDialog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class CourseSelecter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> branch = new ArrayList<>();
    ArrayList<String> year = new ArrayList<>();
    ArrayList<String> division = new ArrayList<>();
    ArrayList<String> sem = new ArrayList<>();
    public static List<String> subjects = new ArrayList<>();
    List<String> elec1 = new ArrayList<>();
    List<String> elec2 = new ArrayList<>();
    List<String> elec3 = new ArrayList<>();

    List<String> subjectListSpinner = new ArrayList<>();
    RecyclerView rcShowSubject;
    String selectedBranch="", selectedYear="", selectedDivision="A", selectedSem="", selectedElective1="", selectedElective2="", selectedElective3="";
    Spinner branchSpinner, yearSpinner, divisionSpinner, academicYearSelector;
    LinearLayout elect;
    CardView cardEle1, cardEle2, cardEle3;
    ImageView back;
    boolean ele1Present , ele2Present , ele3Present;

    Button submit, btnAddSubject;
    SharedPreferences sharedPreferences;
    LoadingDialog loadingDialog;
    SpinnerDialog spinnerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selecter);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();

        back = findViewById(R.id.back);
        submit = findViewById(R.id.submitBtn);

        btnAddSubject = findViewById(R.id.btnAddSubject);


        rcShowSubject = findViewById(R.id.rcShowSubject);

        branchSpinner = findViewById(R.id.branchSelector);
        yearSpinner = findViewById(R.id.yearSelector);
        divisionSpinner = findViewById(R.id.divisionSelector);
        academicYearSelector = findViewById(R.id.academicYearSelector);

        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);

        back.setOnClickListener(v -> onBackPressed());
        submit.setOnClickListener(v ->
                showConfirmationDialog()
        );

        String[] subjectstring = getResources().getStringArray(R.array.subjectList);
        subjectListSpinner = new ArrayList<>(Arrays.asList(subjectstring));

        spinnerDialog=new SpinnerDialog((Activity) CourseSelecter.this, (ArrayList<String>) subjectListSpinner,"Select or Search Subject","Close");// With No Animation
        spinnerDialog=new SpinnerDialog((Activity) CourseSelecter.this, (ArrayList<String>) subjectListSpinner,"Select or Search Subject",R.style.DialogAnimations_SmileWindow,"Close");// With 	Animation

        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default


        spinnerDialog.bindOnSpinerListener((item, position) -> {
            Toast.makeText(CourseSelecter.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
            subjects.add(item);
            setSubjectAdapter();
        });

        btnAddSubject.setOnClickListener(v->{
            spinnerDialog.showSpinerDialog();
        });

        makeYearArray();
        makeSemArray();
        chooseBranch();
        setSubjectAdapter();
    }

    private void setSubjectAdapter(){

        // Example in an Activity
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcShowSubject.setLayoutManager(layoutManager);

        SelectSubjectAdapter adapter = new SelectSubjectAdapter(subjects);
        rcShowSubject.setAdapter(adapter);

    }


    private void chooseBranch() {
        branch.add("Choose Branch");
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference courseCollectionRef = firestore.collection("Course");

        courseCollectionRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                // Add the document name to the ArrayList
                                branch.add(document.getId());
                            }
                            // Create the adapter and set it for the spinner
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(CourseSelecter.this,
                                    android.R.layout.simple_spinner_item, branch);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            branchSpinner.setAdapter(adapter);
                            branchSpinner.setOnItemSelectedListener(CourseSelecter.this);
                        }
                    } else {
                        Log.d("Fetch Error", "Error getting documents: " + task.getException());
                    }
                    loadingDialog.stopLoading();
                });
    }

    private void chooseYear() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CourseSelecter.this,
                android.R.layout.simple_spinner_item, year);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setOnItemSelectedListener(CourseSelecter.this);
    }

    private void chooseDivision() {

        division.clear();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference courseCollectionRef = firestore.collection("Course")
                .document(selectedBranch)
                .collection(selectedYear);

        courseCollectionRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                // Add the document name to the ArrayList
                                division.add(document.getId());
                            }
                            // Create the adapter and set it for the spinner
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(CourseSelecter.this,
                                    android.R.layout.simple_spinner_item, division);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            divisionSpinner.setAdapter(adapter);
                            divisionSpinner.setOnItemSelectedListener(CourseSelecter.this);
                        }
                    } else {
                        Log.d("Fetch Error", "Error getting documents: " + task.getException());
                    }
                    loadingDialog.stopLoading();
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewId = parent.getId();
        if(viewId == R.id.branchSelector){

            selectedBranch = parent.getItemAtPosition(position).toString();
            if(!selectedBranch.equals("Select Branch")) {
                chooseYear();
            }

        } else if (viewId == R.id.yearSelector) {

            // Year spinner selection listener
            selectedYear = parent.getItemAtPosition(position).toString();
            if(!selectedYear.equals("Select Year")) {
                chooseDivision();
                loadingDialog.startLoading();
            }else {
                division.clear();
            }


        } else if (viewId == R.id.divisionSelector) {

            // Division spinner selection listener
            selectedDivision = parent.getItemAtPosition(position).toString();


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void makeYearArray(){
        year.add("Select Year");
        year.add("FY");
        year.add("SY");
        year.add("TY");
        year.add("Btech");
    }

    private void makeSemArray(){
        sem.add("Semester-1");
        sem.add("Semester-2");
    }


    private void sendDataToFireStore(){


        Map<String, Object> data = new HashMap<>();

        for (int i = 0; i < subjects.size(); i++) {
            String key = subjects.get(i);
            data.put(key, 0);
            // add in teacher attendance list
            addDocumentToCollection(selectedBranch,selectedYear,selectedDivision,key,sharedPreferences.getString("regNum",""));
        }

        Map<String, Object> dataP = new HashMap<>();
        dataP.put("branch", selectedBranch);
        dataP.put("year", selectedYear);
        dataP.put("division", selectedDivision);
        dataP.put("sem", selectedSem);
        dataP.put("academicYear", academicYearSelector.getSelectedItem().toString());

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef1 = firestore.collection("StudentLogin").document(sharedPreferences.getString("email",""));
        docRef1.update(dataP);



//        DocumentReference docRef = firestore.collection("Students").
//                document(selectedBranch).
//                collection(selectedYear).
//                document(selectedDivision).
//                collection(sharedPreferences.getString("regNum","")).
//                document("Attendance");
//        docRef.set(data)

        DocumentReference docRef = firestore.collection("Students").
                document(sharedPreferences.getString("regNum",""));

        docRef.set(data)
                .addOnSuccessListener(aVoid -> {
                    SharedPreferences.Editor preferences = sharedPreferences.edit();
                    preferences.putString("branch",selectedBranch);
                    preferences.putString("year",selectedYear);
                    preferences.putString("division",selectedDivision);
                    preferences.putString("semester",selectedSem);
                    preferences.putString("academicYear",academicYearSelector.getSelectedItem().toString());
                    preferences.apply();

//                    for (int i = 0; i < subjects.size(); i++) {
//                        String key = subjects.get(i);
//                        addDocumentToCollection(selectedBranch,selectedYear,selectedDivision,key,sharedPreferences.getString("regNum",""));
//                    }
                    loadingDialog.stopLoading();
                    Toast.makeText(CourseSelecter.this, "Selection Successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    loadingDialog.stopLoading();
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                });
    }



    private void addDocumentToCollection(String selectedBranch, String selectedYear, String selectedDivision, String subject, String reg) {


        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//        DocumentReference documentReference = firestore.collection("Teachers")
//                .document(selectedBranch)
//                .collection(selectedYear)
//                .document(selectedDivision)
//                .collection(subject)
//                .document(reg);

        DocumentReference documentReference = firestore.collection("Teachers")
                .document(academicYearSelector.getSelectedItem().toString())
                .collection(subject)
                .document(selectedDivision)
                .collection("students")
                .document(reg);

// Create a HashMap with the field-value pair you want to update
        Map<String, Object> data = new HashMap<>();
        data.put("test", "test");

        documentReference.set(data).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });


    }



    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your Selected Subjects");
        String subString = "";
        for (int i = 0; i < subjects.size(); i++) {
            subString = subString+"• "+subjects.get(i)+"\n\n";
        }
        builder.setMessage(subString);

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            // Call the logout function
            sendDataToFireStore();
            loadingDialog.startLoading();
        });

        builder.setNegativeButton("Reselect", (dialog, which) -> {
            // Dismiss the dialog
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}




