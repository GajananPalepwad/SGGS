package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
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
    TextView textView;
    boolean ele1Present , ele2Present , ele3Present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selecter);
        elect = findViewById(R.id.elective);
        elect.setVisibility(View.GONE);
//        textView = findViewById(R.id.textView5);
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
        makeYearArray();
        makeSemArray();
        chooseBranch();

    }

    private void chooseBranch() {

        branch.add("Select Branch");

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

        } else if (viewId == R.id.subjectSelector1) {

            // Division spinner selection listener
            selectedElective1 = parent.getItemAtPosition(position).toString();
            elec2.remove(selectedElective1);
            chooseElective2();
        } else if (viewId == R.id.subjectSelector2) {

            // Division spinner selection listener
            selectedElective2 = parent.getItemAtPosition(position).toString();
            elec3.remove(selectedElective2);
            chooseElective3();
        } else if (viewId == R.id.subjectSelector3) {

            // Division spinner selection listener
            selectedElective3 = parent.getItemAtPosition(position).toString();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void makeYearArray(){
        year.add("Select Year");
        year.add("FY");
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
//                            textView.setText(subjects + "\n"+elec1+"\n"+elec2+"\n"+elec3);
                            // Print the results

//                            for (List<Object> documentFields : nonEmptyDocumentsFields) {
//
//                                Toast.makeText(this, "Document ID: " + documentFields.get(0), Toast.LENGTH_SHORT).show();
//                             //   Toast.makeText(this, "Fields: " + documentFields.subList(1, documentFields.size()), Toast.LENGTH_SHORT).show();
//
//                            }
                        }
                    } else {
                        Log.d("Fetch Error", "Error getting documents: " + task.getException());
                    }
                });
    }
}




