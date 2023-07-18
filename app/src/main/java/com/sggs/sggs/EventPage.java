package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sggs.sggs.adapters.EventAdapter;
import com.sggs.sggs.loadingAnimation.LoadingDialog;
import com.sggs.sggs.model.EventModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

public class EventPage extends AppCompatActivity {

    ArrayList<EventModel> eventList;
    EventAdapter adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferences;
    RecyclerView recyclerView;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerView);
        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        preferences = sharedPreferences.edit();
        showEvent();


    }

    private void showEvent() {

        eventList = new ArrayList<>();
        adapter = new EventAdapter(eventList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        try {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Events")
                    .document("list")
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> documentData = documentSnapshot.getData();

                            // Access the data in the document
                            for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue().toString();
                                eventList.add(new EventModel(key, value));
                            }

                            eventList.sort((o1, o2) -> {
                                String time1 = o2.getTime();
                                String time2 = o1.getTime();

                                try {
                                    SimpleDateFormat format = new SimpleDateFormat("dd MMM yy, hh:mm a");
                                    Date date1 = format.parse(time1);
                                    Date date2 = format.parse(time2);
                                    return date1.compareTo(date2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                return 0;
                            });

                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(EventPage.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.stopLoading();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(EventPage.this, "Error getting document" + e, Toast.LENGTH_SHORT).show();
                        loadingDialog.stopLoading();
                    });
        } catch (Exception ignored) {}

    }


}