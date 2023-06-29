package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {
    EditText ETemail;
    Button continueBtn;

    String fullname ;
    String email;
    String regnum;
    String mobileNum;
    String password ;
    String passwordConfirm;
    String selectedImage;


    private BottomSheetDialog bottomSheetDialog;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        ETemail = findViewById(R.id.email);
        continueBtn = findViewById(R.id.continueBtn);
        EditText fullnameEditText = findViewById(R.id.fullname);
        EditText emailEditText = findViewById(R.id.email);
        EditText regnumEditText = findViewById(R.id.regnum);
        EditText mobileNumEditText = findViewById(R.id.mobile_num);
        EditText passwordEditText = findViewById(R.id.password);
        EditText passwordConfirmEditText = findViewById(R.id.passwordConfirm);
        Button continueBtn = findViewById(R.id.continueBtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        String personEmail="";

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
           // String personName = acct.getDisplayName();
            personEmail = acct.getEmail();
            ETemail.setText(personEmail);
        }

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = fullnameEditText.getText().toString();
                email = emailEditText.getText().toString();
                regnum = regnumEditText.getText().toString();
                mobileNum = mobileNumEditText.getText().toString();
                password = passwordEditText.getText().toString();
                passwordConfirm = passwordConfirmEditText.getText().toString();
                String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

                if (!password.matches(passwordPattern)) {
                    Toast.makeText(SignUpPage.this, "Password must contain at least one digit, one lowercase and uppercase letter, one special character, and have a minimum of 8 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (fullname.isEmpty() || email.isEmpty() || regnum.isEmpty() || mobileNum.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                    Toast.makeText(SignUpPage.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(SignUpPage.this, "Both password should match", Toast.LENGTH_SHORT).show();
                    return;
                }
                showSettingsBottomSheetDialog();
            }
        });

        if(personEmail.charAt(0)=='t'
                && personEmail.charAt(1)=='e'
                && personEmail.charAt(2)=='s'
                && personEmail.charAt(3)=='t'
                ){
//            finish();
//            Intent intent = new Intent(LoginToHome.this, home.class);
//            startActivity(intent);
        }

        else if(personEmail.charAt(personEmail.length()-1)!='n'
                && personEmail.charAt(personEmail.length()-2)!='i'
                && personEmail.charAt(personEmail.length()-3)!='.'
                && personEmail.charAt(personEmail.length()-4)!='c'
                && personEmail.charAt(personEmail.length()-5)!='a'
                && personEmail.charAt(personEmail.length()-6)!='.'
                && personEmail.charAt(personEmail.length()-7)!='s'
                && personEmail.charAt(personEmail.length()-8)!='g'
                && personEmail.charAt(personEmail.length()-9)!='g'
                && personEmail.charAt(personEmail.length()-10)!='s'
                && personEmail.charAt(personEmail.length()-11)!='@'){signOut();}

            else if(personEmail.charAt(0)=='2'){
//
//                finish();
//                Intent intent = new Intent(LoginToHome.this, HomeForStudents.class);
//                startActivity(intent);
            }

            else if(personEmail.charAt(0)!='2' && personEmail.charAt(personEmail.length()-1)=='n'
                    && personEmail.charAt(personEmail.length()-2)=='i'
                    && personEmail.charAt(personEmail.length()-3)=='.'
                    && personEmail.charAt(personEmail.length()-4)=='c'
                    && personEmail.charAt(personEmail.length()-5)=='a'
                    && personEmail.charAt(personEmail.length()-6)=='.'
                    && personEmail.charAt(personEmail.length()-7)=='s'
                    && personEmail.charAt(personEmail.length()-8)=='g'
                    && personEmail.charAt(personEmail.length()-9)=='g'
                    && personEmail.charAt(personEmail.length()-10)=='s'
                    && personEmail.charAt(personEmail.length()-11)=='@'){

//                finish();
//                Intent intent = new Intent(LoginToHome.this, home.class);
//                startActivity(intent);
            }
            else{
                signOut();
            }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        signOut();
    }

    @Override
    protected void onPause() {
        super.onPause();
        signOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signOut();
    }

    private void checkAndDataToFireStore(){


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference studentLoginRef = db.collection("StudentLogin");

// Check if the email already exists
        studentLoginRef
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            // Email already exists
                            Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            // Email is unique, proceed to store the data in Firestore
                            DocumentReference documentRef = studentLoginRef.document(email);

                            Map<String, Object> user = new HashMap<>();
                            user.put("fullName", fullname.toUpperCase());
                            user.put("img", selectedImage);
                            user.put("email", email);
                            user.put("regNum", regnum.toUpperCase());
                            user.put("mobileNum", mobileNum);
                            user.put("password", password);

                            documentRef
                                    .set(user)
                                    .addOnSuccessListener(documentReference -> {
                                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        signOut();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        // Error occurred while checking for email existence
                        Toast.makeText(this, "Error occurred while checking email", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(SignUpPage.this,GoogleLoginPage.class));
            }
        });

    }

    public void signOut1(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SignUpPage.this, "PLEASE LOGIN WITH SGGS EMAIL ONLY", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(SignUpPage.this,GoogleLoginPage.class));
            }
        });

    }
    ImageView selectedImageView = null;
    private void showSettingsBottomSheetDialog() {


        // Inflate the layout for the BottomSheetDialog
        View bottomSheetView = getLayoutInflater().inflate(R.layout.avatar_bottomsheet, (ConstraintLayout) findViewById(R.id.setting_sheet));

        ImageView boy1 = bottomSheetView.findViewById(R.id.boy1);
        ImageView boy2 = bottomSheetView.findViewById(R.id.boy2);
        ImageView boy3 = bottomSheetView.findViewById(R.id.boy3);

        ImageView girl1 = bottomSheetView.findViewById(R.id.girl1);
        ImageView girl2 = bottomSheetView.findViewById(R.id.girl2);
        ImageView girl3 = bottomSheetView.findViewById(R.id.girl3);

        Button signup = bottomSheetView.findViewById(R.id.signupBtn);

        boy1.setOnClickListener(view -> {
            selectedImage = "boy1";

            if (selectedImageView != null) {
                selectedImageView.setColorFilter(null);
            }
            selectedImageView = boy1;
            int highlightColor = Color.parseColor("#330000FF");
            boy1.setColorFilter(highlightColor);

        });

        boy2.setOnClickListener(view -> {
            selectedImage = "boy2";
            if (selectedImageView != null) {
                selectedImageView.setColorFilter(null);
            }
            selectedImageView = boy2;
            int highlightColor = Color.parseColor("#330000FF");
            boy2.setColorFilter(highlightColor);
        });

        boy3.setOnClickListener(view -> {
            selectedImage = "boy3";
            if (selectedImageView != null) {
                selectedImageView.setColorFilter(null);
            }
            selectedImageView = boy3;
            int highlightColor = Color.parseColor("#330000FF");
            boy3.setColorFilter(highlightColor);
        });

        girl1.setOnClickListener(view -> {
            selectedImage = "girl1";
            if (selectedImageView != null) {
                selectedImageView.setColorFilter(null);
            }
            selectedImageView = girl1;
            int highlightColor = Color.parseColor("#330000FF");
            girl1.setColorFilter(highlightColor);
        });

        girl2.setOnClickListener(view -> {
            selectedImage = "girl2";
            if (selectedImageView != null) {
                selectedImageView.setColorFilter(null);
            }
            selectedImageView = girl2;
            int highlightColor = Color.parseColor("#330000FF");
            girl2.setColorFilter(highlightColor);
        });

        girl3.setOnClickListener(view -> {
            selectedImage = "girl3";
            if (selectedImageView != null) {
                selectedImageView.setColorFilter(null);
            }
            selectedImageView = girl3;
            int highlightColor = Color.parseColor("#330000FF");
            girl3.setColorFilter(highlightColor);
        });

        signup.setOnClickListener(view -> {
            if(!selectedImage.isEmpty()) {
                checkAndDataToFireStore();
                bottomSheetDialog.dismiss();
            }else{
                Toast.makeText(this, "Choose any Avatar", Toast.LENGTH_SHORT).show();
            }
        });


        // Create the BottomSheetDialog
        bottomSheetDialog = new BottomSheetDialog(SignUpPage.this,R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }



}