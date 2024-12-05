package com.example.gymapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class notesAdapter extends FirestoreRecyclerAdapter<Note, notesAdapter.notesviewholder>{


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public notesAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {

        super(options);
        this.context =context;
    }

    @Override
    protected void onBindViewHolder(@NonNull notesviewholder holder, int i, @NonNull Note note) {

        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
        holder.timestamp.setText(Utility.timestamptostring(note.timestamp));

        holder.itemView.setOnClickListener(v ->{


            Intent intent = new Intent(context, NoteDestils.class);
            intent.putExtra("Día", note.title);
            intent.putExtra("Ejercicio", note.content);
            String docid = this.getSnapshots().getSnapshot(i).getId();
            intent.putExtra("docid",docid);
            context.startActivity(intent);
        });



    }

    @NonNull
    @Override
    public notesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_sample_layout, viewGroup, false);

        return new notesviewholder(view);
    }

    public class notesviewholder extends RecyclerView.ViewHolder{

        TextView title, content, timestamp;

        public notesviewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_textview_id);
            content = itemView.findViewById(R.id.content_textview_id);
            timestamp = itemView.findViewById(R.id.timestampt_textview_id);

        }
    }
}
