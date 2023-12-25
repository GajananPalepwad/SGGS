package com.sggs.sggs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sggs.sggs.loadingAnimation.LoadingDialog;
//import com.sggs.sggs.notification.FcmNotificationsSender;

public class GoogleLoginPage extends AppCompatActivity {

    private GoogleSignInClient client;
    TextView regBtn;
    EditText emailInput;
    EditText passwordInput;
    Button signinBtn;

    String tokenString="";
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login_page);

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

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            Intent intent = new Intent(GoogleLoginPage.this, SignUpPage.class);
            finish();
            startActivity(intent);
        }


        FirebaseMessaging.getInstance().subscribeToTopic("notification")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Devices subscribed successfully
                    } else {
                        // Failed to subscribe devices
                    }
                });

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        client = GoogleSignIn.getClient(this, options);
        regBtn.setOnClickListener(v -> {
            loadingDialog.startLoading();
            Intent i = client.getSignInIntent();
            startActivityForResult(i, 1234);
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        progressBar.setVisibility(View.VISIBLE);
        loadingDialog.stopLoading();
        loadingDialog.startLoading();
            if (requestCode == 1234) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);

                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener(task1 -> {
                               // progressBar.setVisibility(View.GONE);
                                if (task1.isSuccessful()) {
                                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                                    loadingDialog.stopLoading();
                                    if(acct!=null) {
                                        String emailString = acct.getEmail();
                                        if (emailString.endsWith("@gmail.com")){
                                            client.signOut().addOnCompleteListener(task2 -> {
                                                Toast.makeText(GoogleLoginPage.this, "PLEASE LOGIN WITH SGGS EMAIL ONLY", Toast.LENGTH_SHORT).show();
                                            });
                                        }else{
                                            Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                                            startActivity(intent);
                                            finish();
                                        }


                                    }

                                } else {
                                    loadingDialog.stopLoading();
                                    Toast.makeText(GoogleLoginPage.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                } catch (ApiException e) {
                    loadingDialog.stopLoading();
                    e.printStackTrace();
                }

            }else {
                loadingDialog.stopLoading();
            }
    }


    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                tokenString = task.getResult();
            } else {
                // Handle the case when token retrieval fails
                Toast.makeText(GoogleLoginPage.this, "Failed to retrieve token", Toast.LENGTH_LONG).show();
            }
        });
    }

//    private void sendNotification(){
//        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
//                tokenString,
//                "SGGS",
//                "Login Successful",
//                getApplicationContext(),
//                GoogleLoginPage.this
//        );
//
//        notificationsSender.SendNotifications();
//    }



    private void checkLogin(){

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("StudentLogin").document(email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {

                            if(document.getString("password").equals(password)) {
                                SharedPreferences sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
                                SharedPreferences.Editor preferences = sharedPreferences.edit();

                                preferences.putString("email", email);
                                preferences.putString("fullName", document.getString("fullName"));
                                preferences.putString("regNum", document.getString("regNum"));
                                preferences.putString("mobileNum", document.getString("mobileNum"));
                                preferences.putString("img", document.getString("img"));
                                preferences.putString("branch", document.getString("branch"));
                                preferences.putString("year", document.getString("year"));
                                preferences.putString("division", document.getString("division"));
                                preferences.putString("semester", document.getString("sem"));
                                preferences.putString("academicYear", document.getString("academicYear"));
                                preferences.apply();
//                                sendNotification();
//                                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(GoogleLoginPage.this, Home.class);
                                startActivity(intent);
                                loadingDialog.stopLoading();
                                finish();
                            }else{
                                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                                loadingDialog.stopLoading();
                            }
                        } else {
                            // Input does not match database
                            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
                            loadingDialog.stopLoading();
                        }
                    } else {
                        // Error occurred while querying the database
                        Toast.makeText(this, "Error occurred while logging in", Toast.LENGTH_SHORT).show();
                        loadingDialog.stopLoading();
                    }

                });
    }

    public void feedback(View view){
        Uri uri = Uri.parse("https://forms.gle/fDDqepCVhLhHbEmc8");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}