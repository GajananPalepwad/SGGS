package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class CourseSelecter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> branch = new ArrayList<>();
    ArrayList<String> year = new ArrayList<>();
    ArrayList<String> division = new ArrayList<>();

    String selectedBranch, selectedYear, selectedDivision;
    Spinner branchSpinner, yearSpinner, divisionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selecter);
        makeYearArray();
        branchSpinner = findViewById(R.id.branchSelector);
        yearSpinner = findViewById(R.id.yearSelector);
        divisionSpinner = findViewById(R.id.divisionSelector);
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
}
