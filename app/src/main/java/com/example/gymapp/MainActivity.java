package com.example.gymapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNoteBtn;
    ImageButton menuBtn;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    notesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNoteBtn = findViewById(R.id.addNoteBtn);
        menuBtn = findViewById(R.id.menu_Btn);
        recyclerView = findViewById(R.id.recyclearviewId);
        db =FirebaseFirestore.getInstance();

        addNoteBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), NoteDestils.class)));

        menuBtn.setOnClickListener(v -> logout());

        recyclerviewsetup();
    }

    private void recyclerviewsetup() {
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        String userid = currentuser.getUid();


        Query query =  db.collection("Notes").document(userid).collection("my_routines").orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions <Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class).build();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new notesAdapter(options, this);
        recyclerView.setAdapter(adapter);

    }

    void logout(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, menuBtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}