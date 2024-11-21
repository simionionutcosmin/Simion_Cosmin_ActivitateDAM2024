package com.example.seminar4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListaImagini extends AppCompatActivity {

    List<Bitmap> imagini = new ArrayList<>();
    List<ImaginiInsula> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_imagini);

        List<String> linkuriImagini = new ArrayList<>();
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/6/6e/Maldives_Islands.jpg");
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/thumb/7/78/Bora_Bora_%2816542797633%29.jpg/1920px-Bora_Bora_%2816542797633%29.jpg");
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/3/37/Seychelles_La_Digue.jpg");
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/5/59/Phuket_Thailand_Patong_Beach.jpg");
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/3/39/Bali_Indonesia_Beach.jpg");

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            for (String link : linkuriImagini) {
                try {
                    URL url = new URL(link);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.connect();
                    if (con.getResponseCode() == 200) {
                        InputStream is = con.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        imagini.add(bitmap);
                        is.close();
                    } else {
                        Log.e("ConnectionError", "Failed to connect: " + link);
                    }
                } catch (Exception e) {
                    Log.e("DownloadError", "Error downloading image: " + link, e);
                }
            }

            Log.d("ImageDebug", "Numărul de imagini descărcate: " + imagini.size());
            handler.post(() -> {
                if (imagini.isEmpty()) {
                    Log.e("DataError", "No images were retrieved");
                    return;
                }

                lista.add(new ImaginiInsula("Insula Maldive", imagini.get(0), "https://upload.wikimedia.org/wikipedia/commons/6/6e/Maldives_Islands.jpg"));
                lista.add(new ImaginiInsula("Insula Bora Bora", imagini.get(1), "https://upload.wikimedia.org/wikipedia/commons/thumb/7/78/Bora_Bora_%2816542797633%29.jpg/1920px-Bora_Bora_%2816542797633%29.jpg"));
                lista.add(new ImaginiInsula("Insula Seychelles", imagini.get(2), "https://upload.wikimedia.org/wikipedia/commons/3/37/Seychelles_La_Digue.jpg"));
                lista.add(new ImaginiInsula("Insula Phuket", imagini.get(3), "https://upload.wikimedia.org/wikipedia/commons/5/59/Phuket_Thailand_Patong_Beach.jpg"));
                lista.add(new ImaginiInsula("Insula Bali", imagini.get(4), "https://upload.wikimedia.org/wikipedia/commons/3/39/Bali_Indonesia_Beach.jpg"));


                ListView lv = findViewById(R.id.imagini);
                ImagineAdapter adapter = new ImagineAdapter(getApplicationContext(), R.layout.layout_img, lista);
                lv.setAdapter(adapter);
                Log.d("Debug", "Adapter setat cu " + lista.size() + " elemente");
            });
        });

        ListView lv = findViewById(R.id.imagini);
        lv.setOnItemClickListener((parent, view, i, id) -> {
            Intent it = new Intent(getApplicationContext(), WebViewActivity.class);
            it.putExtra("link", lista.get(i).getLink());
            startActivity(it);
        });
    }
}