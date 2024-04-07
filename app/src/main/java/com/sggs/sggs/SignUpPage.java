package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sggs.sggs.loadingAnimation.LoadingDialog;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpPage extends AppCompatActivity {
    EditText ETemail;
    Button continueBtn;
    String fullname , otp;
    String email;
    String regnum;
    String mobileNum;
    String password ;
    String passwordConfirm;
    String selectedImage;
    private BottomSheetDialog bottomSheetDialog;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        loadingDialog = new LoadingDialog(this);


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

        continueBtn.setOnClickListener(v -> {

            fullname = fullnameEditText.getText().toString();
            email = emailEditText.getText().toString();
            regnum = regnumEditText.getText().toString().replaceAll("\\s", "");
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
        });

    }


    @SuppressLint("StaticFieldLeak")
    private void checkAndAddDataToDB(){
        loadingDialog.startLoading();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("email_id",email.trim())
                            .addFormDataPart("full_name",fullname.trim())
                            .addFormDataPart("reg_id",regnum.trim().toUpperCase())
                            .addFormDataPart("mobile_no",mobileNum.trim())
                            .addFormDataPart("password",password.trim())
                            .addFormDataPart("image",selectedImage)
                            .build();
                    Request request = new Request.Builder()
                            .url(getString(R.string.api_link)+"register.php")
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
                    String message = jsonObject.get("Message").getAsString();

                    Toast.makeText(SignUpPage.this, message, Toast.LENGTH_SHORT).show();
                    loadingDialog.stopLoading();
                    onBackPressed();
                }else {
                    loadingDialog.stopLoading();
                }
            }
        }.execute();


    }


    ImageView selectedImageView = null;
    private void showSettingsBottomSheetDialog() {
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
                otpPopup();
                bottomSheetDialog.dismiss();
            }else{
                Toast.makeText(this, "Choose any Avatar", Toast.LENGTH_SHORT).show();
            }
        });

        bottomSheetDialog = new BottomSheetDialog(SignUpPage.this,R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    Dialog dialog;
    public void otpPopup(){
        sendEmailOTP();
        dialog = new Dialog(this, R.style.AppBottomSheetDialogTheme);
        dialog.setContentView(R.layout.otp_popup);

        PinView tvOtp = dialog.findViewById(R.id.tvOtp);
        Button btnVerify = dialog.findViewById(R.id.verify);
        TextView btnResend = dialog.findViewById(R.id.resendOTP);

        btnResend.setOnClickListener(view1 -> {
            if(!email.isEmpty()) {
                sendEmailOTP();
            } else {
                Toast.makeText(this, "Please fill the email box", Toast.LENGTH_SHORT).show();
            }
        });

        btnVerify.setOnClickListener(view1 -> {
            String otpEntered = tvOtp.getText().toString();
            if(otpEntered.equals(otp)) {
                checkAndAddDataToDB();
            } else {
                Toast.makeText(this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setCancelable(true);
        dialog.show();

    }


    @SuppressLint("StaticFieldLeak")
    private void sendEmailOTP() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {

                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("email_id", email)
                            .build();
                    Request request = new Request.Builder()
                            .url(getString(R.string.api_link)+"otp_sender.php")
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
                    // Handle the result and update UI

                    Gson gson = new Gson();
                    Map<String, Object> jsonObject = gson.fromJson(result, Map.class);

                    if(jsonObject.get("Error").toString().equals("200")){
                        otp = jsonObject.get("OTP").toString();
                        Toast.makeText(SignUpPage.this, jsonObject.get("Message").toString(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SignUpPage.this, jsonObject.get("Message").toString(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // Handle the error

                }
            }
        }.execute();

    }


}