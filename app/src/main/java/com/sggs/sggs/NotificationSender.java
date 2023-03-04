package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationSender extends AppCompatActivity {

    EditText title, context;
    Button send, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_sender);
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        title=findViewById(R.id.title);
        context=findViewById(R.id.context);
        send=findViewById(R.id.send);
        back=findViewById(R.id.backn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!title.getText().toString().isEmpty() && !context.getText().toString().isEmpty() ){

                   FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",
                           title.getText().toString(),
                           context.getText().toString(),
                           getApplicationContext(),
                           NotificationSender.this);
                   notificationsSender.SendNotifications();
                   Toast.makeText(NotificationSender.this, "NOTIFIED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                   onDestroy();

               }else{
                   Toast.makeText(NotificationSender.this, "PLEASE WRITE TITLE & MESSAGE", Toast.LENGTH_SHORT).show();
               }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}