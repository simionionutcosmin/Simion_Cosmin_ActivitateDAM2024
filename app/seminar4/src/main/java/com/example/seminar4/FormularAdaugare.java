package com.example.seminar4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormularAdaugare extends AppCompatActivity {
    private InsuleDataBase database = null;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formular_adaugare);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseApp.initializeApp(this);
        database = Room.databaseBuilder(this, InsuleDataBase.class, "InsulaDB").build();
        databaseReference = FirebaseDatabase.getInstance().getReference("insula");

        Intent it = getIntent();
        if (it.hasExtra("insula")) {
            Insula insula = it.getParcelableExtra("insula");
            EditText numeEt = findViewById(R.id.nume);
            EditText suprafataEt = findViewById(R.id.suprafata);
            EditText localizareEt = findViewById(R.id.localizare);
            CheckBox locuitaCb = findViewById(R.id.checkBox);

            numeEt.setText(insula.getNume());
            suprafataEt.setText(String.valueOf(insula.getSuprafata()));
            localizareEt.setText(insula.getLocalizare());
            locuitaCb.setChecked(insula.isLocuita());
        }

        Button btnAdaugare = findViewById(R.id.button2);
        btnAdaugare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etNume = findViewById(R.id.nume);
                String nume = etNume.getText().toString();

                EditText etSuprafata = findViewById(R.id.suprafata);
                double suprafata = 0.0;
                try {
                    suprafata = Double.parseDouble(etSuprafata.getText().toString());
                } catch (NumberFormatException e) {
                    etSuprafata.setError("Introdu o valoare validă!");
                    return;
                }

                EditText etLocalizare = findViewById(R.id.localizare);
                String localizare = etLocalizare.getText().toString();

                CheckBox cbLocuita = findViewById(R.id.checkBox);
                boolean locuita = cbLocuita.isChecked();

                Insula insula = new Insula(nume, suprafata, localizare, locuita);

                CheckBox cbOnline = findViewById(R.id.checkBox2);
                if (cbOnline.isChecked()) {
                    databaseReference.push().setValue(insula)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(FormularAdaugare.this, "Componenta salvată cu succes în Firebase", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(FormularAdaugare.this, "Eroare la salvarea în Firebase: " + task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

                Intent it = new Intent();
                it.putExtra("insula", insula);
                setResult(RESULT_OK, it);
                finish();
            }
        });
    }
}