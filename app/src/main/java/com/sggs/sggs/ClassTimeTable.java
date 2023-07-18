package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sggs.sggs.adapters.TimeTableAdapter;
import com.sggs.sggs.loadingAnimation.LoadingDialog;
import com.sggs.sggs.model.TimeTableModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

    private Button buttonMonday, buttonTuesday, buttonWednesday, buttonThursday, buttonFriday;
    ImageView back;
    LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_time_table);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        buttonMonday = findViewById(R.id.mon);
        buttonTuesday = findViewById(R.id.tue);
        buttonWednesday = findViewById(R.id.wed);
        buttonThursday = findViewById(R.id.thu);
        buttonFriday = findViewById(R.id.fri);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);
        preferences = sharedPreferences.edit();

        subjectList = new ArrayList<>();
        recyclerView = findViewById(R.id.timeList);
        chooseWeekDay(getWeekDay());

        buttonMonday.setOnClickListener(view ->{
            showTimetable("Mon");
            loadingDialog.startLoading();
        });
        buttonTuesday.setOnClickListener(view -> {
            showTimetable("Tue");
            loadingDialog.startLoading();
        });
        buttonWednesday.setOnClickListener(view -> {
            showTimetable("Wed");
            loadingDialog.startLoading();
        });
        buttonThursday.setOnClickListener(view -> {
            showTimetable("Thu");
            loadingDialog.startLoading();
        });
        buttonFriday.setOnClickListener(view -> {
            showTimetable("Fri");
            loadingDialog.startLoading();
        });




    }

    private void chooseWeekDay(String weekDay){
        switch (weekDay){
            case "TUESDAY":
                showTimetable("Tue");
                break;
            case "WEDNESDAY":
                showTimetable("Wed");
                break;
            case "THURSDAY":
                showTimetable("Thu");
                break;
            case "FRIDAY":
                showTimetable("Fri");
                break;
            default:
                showTimetable("Mon");
                break;
        }
    }



    private String getWeekDay(){
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }
        DayOfWeek currentDayOfWeek = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDayOfWeek = currentDate.getDayOfWeek();
        }

        return currentDayOfWeek.name();
    }


    private void showTimetable(String weekDay){

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
                    .collection("WeekDay")
                    .document(weekDay)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> documentData = documentSnapshot.getData();

                            // Access the data in the document
                            for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue().toString();
                                subjectList.add(new TimeTableModel(key,value));
                            }

                            subjectList.sort((o1, o2) -> {
                                String time1 = o1.getTime();
                                String time2 = o2.getTime();
                                return time1.compareTo(time2);
                            });

                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ClassTimeTable.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.stopLoading();
                    })
                    .addOnFailureListener(e -> {
                        loadingDialog.stopLoading();
                        Toast.makeText(ClassTimeTable.this, "Error getting document" + e, Toast.LENGTH_SHORT).show();
                    });
        }catch (Exception ignored){}



    }


}