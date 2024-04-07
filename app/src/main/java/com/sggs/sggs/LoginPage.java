package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sggs.sggs.loadingAnimation.LoadingDialog;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import com.sggs.sggs.notification.FcmNotificationsSender;

public class LoginPage extends AppCompatActivity {

    TextView regBtn;
    EditText emailInput;
    EditText passwordInput;
    Button signinBtn;

    String tokenString="";
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        loadingDialog = new LoadingDialog(this);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.password);
        signinBtn = findViewById(R.id.signinBtn);
        regBtn = findViewById(R.id.reg);

        getToken();

        int permissionState = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);
        // If the permission is not granted, request it.
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }

        signinBtn.setOnClickListener(v -> {
            loadingDialog.startLoading();
            checkLogin();
        });


        FirebaseMessaging.getInstance().subscribeToTopic("notification")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Devices subscribed successfully
                    } else {
                        // Failed to subscribe devices
                    }
                });

        regBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this, SignUpPage.class);
            startActivity(intent);
        });

    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                tokenString = task.getResult();
            } else {
                // Handle the case when token retrieval fails
                Toast.makeText(LoginPage.this, "Failed to retrieve token", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void checkLogin(){

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("email_id",email)
                            .addFormDataPart("password",password)
                            .build();
                    Request request = new Request.Builder()
                            .url(getString(R.string.api_link)+"login_api.php")
                            .method("POST", body)
                            .build();
                    Response response = client.newCall(request).execute();
                    return response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {

                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(result, JsonObject.class);

                    String status = jsonObject.get("Status").getAsString();
                    String message = jsonObject.get("message").getAsString();

                    Toast.makeText(LoginPage.this, message, Toast.LENGTH_SHORT).show();

                    if(status.equals("200")) {
                        JsonObject userObject = jsonObject.getAsJsonObject("USER");
                        String email = userObject.get("email_id").getAsString();
                        String fullName = userObject.get("full_name").getAsString();
                        String regId = userObject.get("reg_id").getAsString();
                        String mobileNo = userObject.get("mobile_no").getAsString();
                        String image = userObject.get("Image").getAsString();

                        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
                        SharedPreferences.Editor preferences = sharedPreferences.edit();

                        preferences.putString("email", email);
                        preferences.putString("fullName", fullName);
                        preferences.putString("regNum", regId);
                        preferences.putString("mobileNum", mobileNo);
                        preferences.putString("img", image);
                        preferences.putString("branch", "CSE");
                        preferences.putString("year", "TY");
                        preferences.putString("division", "A");
                        preferences.putString("semester", "sem");
                        preferences.putString("academicYear", getString(R.string.academic_year));
                        preferences.apply();
//                                sendNotification();
//                                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPage.this, Home.class);
                        startActivity(intent);
                        loadingDialog.stopLoading();
                        finish();
                    }
                    loadingDialog.stopLoading();
                }else {
                    loadingDialog.stopLoading();
                }
            }
        }.execute();


    }

    public void feedback(View view){
        Uri uri = Uri.parse("https://forms.gle/fDDqepCVhLhHbEmc8");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}