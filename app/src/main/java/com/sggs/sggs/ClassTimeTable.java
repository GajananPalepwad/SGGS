package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sggs.sggs.adapters.TimeTableAdapter;
import com.sggs.sggs.model.Subject;
import com.sggs.sggs.model.TimeTableModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class ClassTimeTable extends AppCompatActivity {

    TimeTableAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<TimeTableModel> subjectList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_time_table);

        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);
        preferences = sharedPreferences.edit();

        subjectList = new ArrayList<>();
        recyclerView = findViewById(R.id.timeList);

        subjectList = new ArrayList<>();
        adapter = new TimeTableAdapter(subjectList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        try{

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TimeTable")
                .document(sharedPreferences.getString("branch",""))
                .collection(sharedPreferences.getString("year",""))
                .document(sharedPreferences.getString("division",""))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> documentData = documentSnapshot.getData();

                        // Access the data in the document
                        for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue().toString();
                            subjectList.add(new TimeTableModel(key, value));
                        }

                        Collections.sort(subjectList, new Comparator<TimeTableModel>() {
                            @Override
                            public int compare(TimeTableModel o1, TimeTableModel o2) {
                                String[] parts1 = o1.getTimeNteacher().split("\\(");
                                String[] parts2 = o2.getTimeNteacher().split("\\(");
                                String time1 = parts1[0].trim();
                                String time2 = parts2[0].trim();
                                return time1.compareTo(time2);
                            }
                        });


                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ClassTimeTable.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ClassTimeTable.this, "Error getting document" + e, Toast.LENGTH_SHORT).show();
                });
    }catch (Exception ignored){}



    }
}