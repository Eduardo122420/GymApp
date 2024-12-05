package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Pantalla_Carga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_carga);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentuser==null){

                    startActivity(new Intent(getApplicationContext(),Login.class));

                }else {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }



                finish();
            }
        }, 1000);
    }
}