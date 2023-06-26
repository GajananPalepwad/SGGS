package com.sggs.sggs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginToHome extends AppCompatActivity {
    EditText email;
    Button signupBtn;


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_to_home);
        email = findViewById(R.id.email);
        signupBtn = findViewById(R.id.signupBtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        String personEmail="";

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
           // String personName = acct.getDisplayName();
            personEmail = acct.getEmail();
            email.setText(personEmail);
        }

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginToHome.this, home.class);
                startActivity(intent);
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

    public void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(LoginToHome.this, "PLEASE LOGIN WITH SGGS EMAIL ONLY", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(LoginToHome.this,GoogleLoginPage.class));
            }
        });

    }


}