package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText email, password, confirmpassword;
    MaterialButton registerBtn;
    TextView logintxt;
    ProgressBar progressBar;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


            email = findViewById(R.id.emailEd);
            password = findViewById(R.id.passwordEd);
            confirmpassword = findViewById(R.id.confirmpasswordEd);
            registerBtn = findViewById(R.id.registerBtnId);
            logintxt = findViewById(R.id.logintxtId);
            progressBar = findViewById(R.id.progressbar);

            auth = FirebaseAuth.getInstance();

            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    userregister();

                }
            });

            logintxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            });

    }

    private void userregister() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = confirmpassword.getText().toString();

        boolean isvalidate =validate(Email, Password, ConfirmPassword);
        if (!isvalidate){
            return;

            }
        createAccount(Email, Password);
    }

    private void createAccount(String email, String password) {
        changeprograss(true);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            changeprograss(false);

            if (task.isSuccessful()){
                Toast.makeText(RegisterActivity.this,"Register Successfully", Toast.LENGTH_SHORT).show();
                auth.getCurrentUser().sendEmailVerification();
                auth.signOut();
                finish();
            }else {
                Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }

            }
        });

    }


    boolean validate(String Email, String Password, String ConfirmPassword){



            if (!Patterns. EMAIL_ADDRESS. matcher(Email).matches()){
                email.setError("Email Invalid Formate");
                return  false;
            }
            if (Password.length()<6){
                password.setError("Minimum length of 6");
                return false;
            }
            if (!ConfirmPassword.equals(Password)){
                confirmpassword.setError("Different Passwords");
                return false;
            }

            return true;
    }


    boolean changeprograss(boolean inprogress){
        if(inprogress){
            progressBar.setVisibility(View.VISIBLE);
            registerBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            registerBtn.setVisibility(View.VISIBLE);

        }
        return  true;
    }
}