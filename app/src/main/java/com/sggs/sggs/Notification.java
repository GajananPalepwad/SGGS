package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sggs.sggs.adapters.NotificationAdapter;
import com.sggs.sggs.adapters.TimeTableAdapter;
import com.sggs.sggs.loadingAnimation.LoadingDialog;
import com.sggs.sggs.model.NotificationModel;
import com.sggs.sggs.model.TimeTableModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

public class Notification extends AppCompatActivity {

    ArrayList<NotificationModel> notificationList;
    NotificationAdapter adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferences;
    RecyclerView recyclerView;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerView);
        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        preferences = sharedPreferences.edit();
        showNotification();
    }


    private void showNotification() {

        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        try {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Notification")
                    .document("All")
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> documentData = documentSnapshot.getData();

                            // Access the data in the document
                            for (Map.Entry<String, Object> entry : documentData.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue().toString();
                                notificationList.add(new NotificationModel(key, value));
                            }

                            notificationList.sort((o1, o2) -> {
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

                            loadingDialog.stopLoading();
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Notification.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                            loadingDialog.stopLoading();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Notification.this, "Error getting document" + e, Toast.LENGTH_SHORT).show();
                        loadingDialog.stopLoading();
                    });
        } catch (Exception ignored) {}

    }

}