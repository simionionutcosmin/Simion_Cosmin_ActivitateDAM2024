package com.example.seminar4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Insula> insule = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        insule = new ArrayList<>();
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), FormularAdaugare.class);
                startActivityForResult(it, 302);
            }
        });

        Button adaugare = findViewById(R.id.button3);
        adaugare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), ListaInsule.class);
                it.putParcelableArrayListExtra("insule", (ArrayList<? extends Parcelable>) insule);
                startActivity(it);

            }
        });
        Button imagini = findViewById(R.id.button4);
        imagini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), ListaImagini.class);
                startActivity(it);

            }
        });
        Button json = findViewById(R.id.button5);
        json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), InteractiuneJson.class);
                startActivity(it);

            }
        });
        Button btnFav = findViewById(R.id.btnFav);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), MainActivityFavorite.class);
                startActivity(it);
            }
        });

        Button btnTest=findViewById(R.id.button6);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it =new Intent(getApplicationContext(), FormularAdaugare.class);
                startActivity(it);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 302) {
            if (resultCode == RESULT_OK) {
                Insula i = data.getParcelableExtra("insula");
                insule.add(i);

            }

        }

    }
}