package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sggs.sggs.loadingAnimation.LoadingDialog;

public class ExamTimeTable extends AppCompatActivity {

    WebView web;

    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        web = findViewById(R.id.timtabe);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();


        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);


        web.setWebViewClient(new WebViewClient(){
            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setTitle("Loading...");
            }
            @Override public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setTitle(view.getTitle());
                loadingDialog.stopLoading();
            }

        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Sheet1 = ref.child("12n55jC0C4laBANwgTXrkvHgqsCp4o5OYxqYG7YUxYtc").child("SingalLinks").child("ExamTimeTable").child("Url");
        Sheet1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String link=snapshot.getValue(String.class);

                    web.loadUrl(link);

                }
                else {
                    Toast.makeText(ExamTimeTable.this, "PLEASE WAIT FOR TIMETABLE", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.stopLoading();
            }
        });

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());
    }
}