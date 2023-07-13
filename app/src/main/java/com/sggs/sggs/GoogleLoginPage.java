package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class GoogleLoginPage extends AppCompatActivity {

    private GoogleSignInClient client;
    TextView regBtn;
    EditText emailInput;
    EditText passwordInput;
    Button signinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login_page);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.password);
        signinBtn = findViewById(R.id.signinBtn);
        regBtn = findViewById(R.id.reg);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            Intent intent = new Intent(GoogleLoginPage.this, SignUpPage.class);
            finish();
            startActivity(intent);
        }


        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        client = GoogleSignIn.getClient(this, options);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i, 1234);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        progressBar.setVisibility(View.VISIBLE);

            if (requestCode == 1234) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);

                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                   // progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(GoogleLoginPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } catch (ApiException e) {
                    e.printStackTrace();
                }

            }
    }

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
                                preferences.apply();

                                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(GoogleLoginPage.this, Home.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Input does not match database
                            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error occurred while querying the database
                        Toast.makeText(this, "Error occurred while logging in", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void feedback(View view){
        Uri uri = Uri.parse("https://forms.gle/fDDqepCVhLhHbEmc8");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}