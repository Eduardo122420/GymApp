package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextInputEditText email, password;
    MaterialButton loginBtn;
    TextView registertxt;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        email= findViewById(R.id.emailEd);
        password= findViewById(R.id.passwordEd);
        loginBtn= findViewById(R.id.loginBtnId);
        registertxt= findViewById(R.id.registerTxtId);
        progressBar= findViewById(R.id.progressbar);
        auth= FirebaseAuth.getInstance();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

    }

    private void userLogin() {

        String Email= email.getText().toString();
        String Password= password.getText().toString();

        boolean isvalidate =validate(Email, Password);
        if (!isvalidate){
            return;

        }

        loginuser(Email, Password);

    }

    private void loginuser(String email, String password) {

        changeprograss(false);

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                changeprograss(false);

                if (task.isSuccessful()){

                    if (auth.getCurrentUser().isEmailVerified()){

                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();

                    }else {
                        Toast.makeText(Login.this, "Correo no Verificado", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(Login.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    boolean validate(String Email, String Password){



        if (!Patterns. EMAIL_ADDRESS. matcher(Email).matches()){
            email.setError("Formato de Correo Invalido");
            return  false;
        }
        if (Password.length()<6){
            password.setError("Ocupas una Contraseña de Mínimo 6 Carácteres");
            return false;
        }

        return true;
    }


    boolean changeprograss(boolean inprogress){
        if(inprogress){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);

        }
        return  true;
    }

}