package com.example.seminar4;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FIrebase extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference databaseReference;
    private List<Insula> ListaInsule;
    private ArrayAdapter<Insula> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        listView = findViewById(R.id.firebaseListView);
        ListaInsule = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListaInsule);
        listView.setAdapter(adapter);

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("components");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListaInsule.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Insula component = dataSnapshot.getValue(Insula.class);
                    if (component != null) {
                        ListaInsule.add(component);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FIrebase.this,
                        "Eroare la citirea din Firebase: " + error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}