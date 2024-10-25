package com.example.seminar4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormularAdaugare extends AppCompatActivity {

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

                Intent it = new Intent();
                it.putExtra("insula", insula);
                setResult(RESULT_OK, it);
                finish();
            }
        });
    }
}