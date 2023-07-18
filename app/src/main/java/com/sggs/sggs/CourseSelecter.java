package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.sggs.sggs.loadingAnimation.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CourseSelecter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> branch = new ArrayList<>();
    ArrayList<String> year = new ArrayList<>();
    ArrayList<String> division = new ArrayList<>();
    ArrayList<String> sem = new ArrayList<>();
    List<String> subjects = new ArrayList<>();
    List<String> elec1 = new ArrayList<>();
    List<String> elec2 = new ArrayList<>();
    List<String> elec3 = new ArrayList<>();

    String selectedBranch="", selectedYear="", selectedDivision="A", selectedSem="", selectedElective1="", selectedElective2="", selectedElective3="";
    Spinner branchSpinner, yearSpinner, divisionSpinner, semSpinner, electiveSpinner1, electiveSpinner2, electiveSpinner3;
    LinearLayout elect;
    CardView cardEle1, cardEle2, cardEle3;
    ImageView back;
    boolean ele1Present , ele2Present , ele3Present;

    Button submit;
    SharedPreferences sharedPreferences;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selecter);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();

        elect = findViewById(R.id.elective);
        elect.setVisibility(View.GONE);
        back = findViewById(R.id.back);
        submit = findViewById(R.id.submitBtn);
        cardEle1 = findViewById(R.id.card1);
        cardEle2 = findViewById(R.id.card2);
        cardEle3 = findViewById(R.id.card3);
        branchSpinner = findViewById(R.id.branchSelector);
        yearSpinner = findViewById(R.id.yearSelector);
        divisionSpinner = findViewById(R.id.divisionSelector);
        semSpinner = findViewById(R.id.semSelector);
        electiveSpinner1 = findViewById(R.id.subjectSelector1);
        electiveSpinner2 = findViewById(R.id.subjectSelector2);
        electiveSpinner3 = findViewById(R.id.subjectSelector3);

        back.setOnClickListener(v -> onBackPressed());
        submit.setOnClickListener(v -> showConfirmationDialog());
        showFYConfirmationDialog();
        makeYearArray();
        makeSemArray();
        chooseBranch();

    }

    private void showFYConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you First Year?");
        builder.setMessage("If you are FY, you don't need to select Branch and Year");

        builder.setPositiveButton("YES", (dialog, which) -> {
            // Call the logout function
            selectedBranch = "FY";
            selectedYear = "FY";
            branchSpinner.setEnabled(false);
            yearSpinner.setEnabled(false);
            chooseDivision();
            loadingDialog.startLoading();
        });

        builder.setNegativeButton("NO", ((dialog, which) -> {
            dialog.dismiss();
        }));


        AlertDialog dialog = builder.create();
        dialog.show();
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

    private void chooseSem() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CourseSelecter.this,
                android.R.layout.simple_spinner_item, sem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semSpinner.setAdapter(adapter);
        semSpinner.setOnItemSelectedListener(CourseSelecter.this);
    }

    private void chooseElective1() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CourseSelecter.this,
                android.R.layout.simple_spinner_item, elec1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        electiveSpinner1.setAdapter(adapter);
        electiveSpinner1.setOnItemSelectedListener(CourseSelecter.this);
    }

    private void chooseElective2() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CourseSelecter.this,
                android.R.layout.simple_spinner_item, elec2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        electiveSpinner2.setAdapter(adapter);
        electiveSpinner2.setOnItemSelectedListener(CourseSelecter.this);
    }

    private void chooseElective3() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CourseSelecter.this,
                android.R.layout.simple_spinner_item, elec3);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        electiveSpinner3.setAdapter(adapter);
        electiveSpinner3.setOnItemSelectedListener(CourseSelecter.this);
    }

    private void chooseDivision() {

        division.clear();
        division.add("Select Division");

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

            if(!selectedDivision.equals("Select Division")) {
                chooseSem();
            }

        } else if (viewId == R.id.semSelector) {

            // Division spinner selection listener
            selectedSem = parent.getItemAtPosition(position).toString();
            electiveSelection();
            loadingDialog.startLoading();

        } else if (viewId == R.id.subjectSelector1) {

            // Division spinner selection listener
            if(!selectedElective1.equals("")){
                subjects.remove(selectedElective1);
            }
            selectedElective1 = parent.getItemAtPosition(position).toString();
            subjects.add(selectedElective1);
            elec2.remove(selectedElective1);
            chooseElective2();

        } else if (viewId == R.id.subjectSelector2) {

            // Division spinner selection listener
            if(!selectedElective2.equals("")){
                subjects.remove(selectedElective2);
            }
            selectedElective2 = parent.getItemAtPosition(position).toString();
            subjects.add(selectedElective2);
            elec3.remove(selectedElective2);
            chooseElective3();

        } else if (viewId == R.id.subjectSelector3) {

            // Division spinner selection listener
            if(!selectedElective3.equals("")){
                subjects.remove(selectedElective3);
            }
            selectedElective3 = parent.getItemAtPosition(position).toString();
            subjects.add(selectedElective3);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void makeYearArray(){
        year.add("Select Year");
        year.add("SY");
        year.add("TY");
        year.add("Btech");
    }

    private void makeSemArray(){
        sem.add("Select Semester of This Year");
        sem.add("Semester-1");
        sem.add("Semester-2");
    }

    private void electiveSelection() {

        subjects.clear();
        elec1.clear();
        elec2.clear();
        elec3.clear();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference courseCollectionRef = firestore.collection("Course")
                .document(selectedBranch)
                .collection(selectedYear)
                .document(selectedDivision)
                .collection(selectedSem);

        courseCollectionRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();

                            // Lists to store the results
                            List<String> emptyDocuments = new ArrayList<>();
                            List<List<Object>> nonEmptyDocumentsFields = new ArrayList<>();

                            // Process each document
                            for (DocumentSnapshot documentSnapshot : documents) {
                                String documentId = documentSnapshot.getId();
                                if (documentSnapshot.getData() == null) {
                                    // Document is empty
                                    emptyDocuments.add(documentId);
                                } else {
                                    // Document is not empty
                                    List<Object> documentFields = new ArrayList<>();
                                    documentFields.add(documentId);
                                    documentFields.addAll(documentSnapshot.getData().values());
                                    nonEmptyDocumentsFields.add(documentFields);
                                }
                            }
                            String subjectString = "";
                            if(nonEmptyDocumentsFields!=null) {
                                subjectString = nonEmptyDocumentsFields + "";
                            }



                            String[] elements = subjectString.split("\\], \\[");  // Splitting the input string into individual elements

                            for (String element : elements) {
                                element = element.replaceAll("\\[|\\]", "");  // Removing the square brackets from each element

                                String[] subjectsArray = element.split(", ");

                                if (subjectsArray.length == 1) {
                                    subjects.add(subjectsArray[0]);
                                } else if (elec1.isEmpty()) {
                                    elec1.addAll(List.of(subjectsArray));
                                } else if (elec2.isEmpty()) {
                                    elec2.addAll(List.of(subjectsArray));
                                } else {
                                    elec3.addAll(List.of(subjectsArray));
                                }
                            }

                            ele1Present = false;
                            ele2Present = false;
                            ele3Present = false;

                            elect.setVisibility(View.GONE);


                            if(elec1.size()>0){
                                ele1Present = true;
                            }
                            if(elec2.size()>0){
                                ele2Present = true;
                            }
                            if(elec3.size()>0){
                                ele3Present = true;
                            }


                            if(!selectedSem.equals("Select Semester of Year")) {
                                if(ele1Present || ele2Present || ele3Present){
                                    elect.setVisibility(View.VISIBLE);
                                    cardEle1.setVisibility(View.VISIBLE);
                                    cardEle2.setVisibility(View.VISIBLE);
                                    cardEle3.setVisibility(View.VISIBLE);
                                    if (ele1Present && ele2Present && !ele3Present){
                                        elect.setVisibility(View.VISIBLE);
                                        cardEle1.setVisibility(View.VISIBLE);
                                        cardEle2.setVisibility(View.VISIBLE);
                                        cardEle3.setVisibility(View.GONE);
                                    } else if (ele1Present&& !ele2Present && !ele3Present) {
                                        elect.setVisibility(View.VISIBLE);
                                        cardEle1.setVisibility(View.VISIBLE);
                                        cardEle2.setVisibility(View.GONE);
                                        cardEle3.setVisibility(View.GONE);
                                    }

                                }else{
                                    elect.setVisibility(View.GONE);
                                }

                            }else{
                                elect.setVisibility(View.GONE);
                            }

                            chooseElective1();

                        }
                    } else {
                        Toast.makeText(this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                    loadingDialog.stopLoading();
                });
    }


    private void sendDataToFireStore(){

        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);

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

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef1 = firestore.collection("StudentLogin").document(sharedPreferences.getString("email",""));
        docRef1.update(dataP);



        DocumentReference docRef = firestore.collection("Students").
                document(selectedBranch).
                collection(selectedYear).
                document(selectedDivision).
                collection(sharedPreferences.getString("regNum","")).
                document("Attendance");
        docRef.set(data)
                .addOnSuccessListener(aVoid -> {
                    SharedPreferences.Editor preferences = sharedPreferences.edit();
                    preferences.putString("branch",selectedBranch);
                    preferences.putString("year",selectedYear);
                    preferences.putString("division",selectedDivision);
                    preferences.putString("semester",selectedSem);
                    preferences.apply();

                    for (int i = 0; i < subjects.size(); i++) {
                        String key = subjects.get(i);
                        addDocumentToCollection(selectedBranch,selectedYear,selectedDivision,key,sharedPreferences.getString("regNum",""));
                    }

                    Toast.makeText(CourseSelecter.this, "Selection Successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                });
    }



    private void addDocumentToCollection(String selectedBranch, String selectedYear, String selectedDivision, String subject, String reg) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firestore.collection("Teachers")
                .document(selectedBranch)
                .collection(selectedYear)
                .document(selectedDivision)
                .collection(subject)
                .document(reg);

        documentReference.set(new HashMap<>()).addOnSuccessListener(aVoid -> {
            finish();
            loadingDialog.startLoading();
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




