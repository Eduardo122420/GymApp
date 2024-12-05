package com.example.gymapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class NoteDestils extends AppCompatActivity {


        ImageButton saveBtn;
        EditText title, content;
        TextView addnewnoteTitle, deleteBtn;
        FirebaseFirestore db;

        String Title, Content, Docid;
        boolean isEditmode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_destils);

        saveBtn = findViewById(R.id.saveBtn);
        title = findViewById(R.id.titleEd);
        content = findViewById(R.id.contentEd);
        addnewnoteTitle = findViewById(R.id.addnewnoteTitle);
        deleteBtn = findViewById(R.id.deleteBtn);



        db = FirebaseFirestore.getInstance();

        saveBtn.setOnClickListener(v -> addnote());
        deleteBtn.setOnClickListener(v ->  deleteNote());


        Title = getIntent().getStringExtra("Día");
        Content = getIntent().getStringExtra("Ejercicio");
        Docid = getIntent().getStringExtra("docid");



        if (Docid!=null && !Docid.isEmpty()){

            isEditmode = true;

        }
        title.setText(Title);
        content.setText(Content);


        if (isEditmode){
            addnewnoteTitle.setText("Edita tu Rutina");
            deleteBtn.setVisibility(View.VISIBLE);

        }



    }

    private void addnote() {

        String Title = title.getText().toString();
        String Content = content.getText().toString();

        if (Title == null  || Title.isEmpty()){
            title.setError("Debe de Agregar un Día de la Semana");
            return;

        }


        saveNote(Title, Content);

    }

    private void saveNote(String Title, String Content) {

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        String userid = currentuser.getUid();

        Log.d("userid", userid);
        Note note = new Note(Title, Content, Timestamp.now());

        if (isEditmode){
            db.collection("Notes").document(userid).collection("my_routines").document(Docid).set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(NoteDestils.this, "Routine Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(NoteDestils.this, "Routine Not Saved", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }else {

            db.collection("Notes").document(userid).collection("my_routines").document().set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(NoteDestils.this, "Routine Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(NoteDestils.this, "Routine Not Saved", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }


    }

    void deleteNote(){

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        String userid = currentuser.getUid();

        db.collection("Notes").document(userid).collection("my_routines").document(Docid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(NoteDestils.this, "Routine Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(NoteDestils.this, "Routine Not Deleted", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}