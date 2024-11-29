package com.example.seminar4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InteractiuneJson extends AppCompatActivity {
    private List<Situatie> ListaSituatiiDisciplina = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_interactiune_json);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button bt = findViewById(R.id.buttonSit);

        ListaSituatiiDisciplina = new ArrayList<>();


        bt.setOnClickListener((View view) ->{

            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.myLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection connection = null;
                    try {
                        URL url = new URL("https://pdm.ase.ro/situatii.json");
                        connection = (HttpURLConnection) url.openConnection();
                        InputStream is = connection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(is);

                        BufferedReader buffer = new BufferedReader(inputStreamReader);
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while((line = buffer.readLine()) != null){
                            sb.append(line);
                        }

                        JSONObject jsonObject = new JSONObject(sb.toString());
                        JSONArray ArrayObiecte = jsonObject.getJSONArray("situatii");

                        for(int i=0; i< 4; i++) {
                            JSONObject situatie = ArrayObiecte.getJSONObject(i);

                            String disciplina = situatie.getString("disciplina");
                            String activitate = situatie.getString("activitate");
                            int valoare = situatie.getInt("valoare");
                            double pondere = situatie.getDouble("pondere");
                            String data = situatie.getString("data");
                            String descriere = situatie.getString("descriere");

                            ListaSituatiiDisciplina.add(new Situatie(disciplina, activitate, valoare, pondere, data, descriere));
                        }
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ListView lv = findViewById(R.id.listviewSit);
                            ArrayAdapter<Situatie> adapter = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_list_item_1, ListaSituatiiDisciplina);
                            lv.setAdapter(adapter);
                        }
                    });
                }
            });
        });
    }

}