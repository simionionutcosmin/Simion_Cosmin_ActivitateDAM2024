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
        linkuriImagini.add("https://www.google.com/imgres?q=insula%20maldive&imgurl=https%3A%2F%2Fwww.karpaten.ro%2Fblog%2Fwp-content%2Fuploads%2F2024%2F03%2Fmaldive-insulele-banilor-poza-cover.jpg&imgrefurl=https%3A%2F%2Fwww.karpaten.ro%2Fblog%2Finsulele-maldive-descopera-una-dintre-destinatiile-de-vacanta-preferate-ale-starurilor%2F&docid=lkyuhMH3KMnQ9M&tbnid=fwvhAn1lXz-9UM&vet=12ahUKEwie_buypuaKAxUIIRAIHSsGK44QM3oECBoQAA..i&w=1200&h=600&hcb=2&ved=2ahUKEwie_buypuaKAxUIIRAIHSsGK44QM3oECBoQAA");
        linkuriImagini.add("https://www.google.com/imgres?q=insula%20bora%20bora&imgurl=https%3A%2F%2Fwww.cruiseget.com%2Fro%2Fuploads%2Fimg%2Fcities%2Fall2%2FBORA_BORA_bora-bora-g8b57e564a_1920.jpg&imgrefurl=https%3A%2F%2Fwww.cruiseget.com%2Fro%2Finformatii-utile%2Ftari-si-orase-port%2Fbora-bora%2F&docid=O85rWLUUca6VLM&tbnid=lceSlkSdFZRXWM&vet=12ahUKEwiP1LuVpuaKAxWnHxAIHWtkOPcQM3oECBMQAA..i&w=1920&h=1281&hcb=2&ved=2ahUKEwiP1LuVpuaKAxWnHxAIHWtkOPcQM3oECBMQAA");
        linkuriImagini.add("https://www.google.com/imgres?q=insula%20seychelles&imgurl=https%3A%2F%2Flife.ro%2Fwp-content%2Fuploads%2F2019%2F03%2FiStock-501296920.jpg&imgrefurl=https%3A%2F%2Flife.ro%2Fcat-costa-o-vacanta-in-seychelles-paradisul-din-oceanul-indian%2F&docid=e7dEq050NvRGnM&tbnid=JmtLJoK7CjHkxM&vet=12ahUKEwj_-__ApuaKAxUhAxAIHTlmN2kQM3oECGMQAA..i&w=1254&h=837&hcb=2&ved=2ahUKEwj_-__ApuaKAxUhAxAIHTlmN2kQM3oECGMQAA");
        linkuriImagini.add("https://www.google.com/imgres?q=insula%20phuket&imgurl=https%3A%2F%2Fwww.karpaten.ro%2Fblog%2Fwp-content%2Fuploads%2F2023%2F09%2FPhuket-principalele-elemente-de-care-te-poti-bucura-in-aceasta-insula-fermecatoare-insula-James-Bong-cu-stanci-unice.jpg&imgrefurl=https%3A%2F%2Fwww.karpaten.ro%2Fblog%2Fphuket-paradisul-thailandez-ce-asteapta-sa-fie-descoperit-de-oamenii-de-pretutindeni%2F&docid=X2N_Wq7Kh0IIsM&tbnid=7ljhqOoeujde2M&vet=12ahUKEwj-vLTXpuaKAxU-QVUIHSA3IS0QM3oECBcQAA..i&w=900&h=600&hcb=2&ved=2ahUKEwj-vLTXpuaKAxU-QVUIHSA3IS0QM3oECBcQAA");
        linkuriImagini.add("https://www.google.com/imgres?q=insula%20bali&imgurl=https%3A%2F%2Fwww.aerocenter.ro%2FProductContentFileHandler%2F1000%2F1000%2Fcircuite-indonezia-24669.jpg&imgrefurl=https%3A%2F%2Fwww.aerocenter.ro%2Fasia%2Findonezia%2Fdescopera-doua-insule-de-vis-bali-si-lombok&docid=8WG8VQrISoEMfM&tbnid=ZnVkW23vkML-AM&vet=12ahUKEwick7j4puaKAxU-HRAIHbyhA-AQM3oECBgQAA..i&w=1000&h=662&hcb=2&ved=2ahUKEwick7j4puaKAxU-HRAIHbyhA-AQM3oECBgQAA");

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

                lista.add(new ImaginiInsula("Insula Maldive", imagini.get(0), "https://www.google.com/imgres?q=insula%20maldive&imgurl=https%3A%2F%2Fwww.karpaten.ro%2Fblog%2Fwp-content%2Fuploads%2F2024%2F03%2Fmaldive-insulele-banilor-poza-cover.jpg&imgrefurl=https%3A%2F%2Fwww.karpaten.ro%2Fblog%2Finsulele-maldive-descopera-una-dintre-destinatiile-de-vacanta-preferate-ale-starurilor%2F&docid=lkyuhMH3KMnQ9M&tbnid=fwvhAn1lXz-9UM&vet=12ahUKEwie_buypuaKAxUIIRAIHSsGK44QM3oECBoQAA..i&w=1200&h=600&hcb=2&ved=2ahUKEwie_buypuaKAxUIIRAIHSsGK44QM3oECBoQAA"));
                lista.add(new ImaginiInsula("Insula Bora Bora", imagini.get(1), "https://www.google.com/imgres?q=insula%20bora%20bora&imgurl=https%3A%2F%2Fwww.cruiseget.com%2Fro%2Fuploads%2Fimg%2Fcities%2Fall2%2FBORA_BORA_bora-bora-g8b57e564a_1920.jpg&imgrefurl=https%3A%2F%2Fwww.cruiseget.com%2Fro%2Finformatii-utile%2Ftari-si-orase-port%2Fbora-bora%2F&docid=O85rWLUUca6VLM&tbnid=lceSlkSdFZRXWM&vet=12ahUKEwiP1LuVpuaKAxWnHxAIHWtkOPcQM3oECBMQAA..i&w=1920&h=1281&hcb=2&ved=2ahUKEwiP1LuVpuaKAxWnHxAIHWtkOPcQM3oECBMQAA"));
                lista.add(new ImaginiInsula("Insula Seychelles", imagini.get(2), "https://www.google.com/imgres?q=insula%20seychelles&imgurl=https%3A%2F%2Flife.ro%2Fwp-content%2Fuploads%2F2019%2F03%2FiStock-501296920.jpg&imgrefurl=https%3A%2F%2Flife.ro%2Fcat-costa-o-vacanta-in-seychelles-paradisul-din-oceanul-indian%2F&docid=e7dEq050NvRGnM&tbnid=JmtLJoK7CjHkxM&vet=12ahUKEwj_-__ApuaKAxUhAxAIHTlmN2kQM3oECGMQAA..i&w=1254&h=837&hcb=2&ved=2ahUKEwj_-__ApuaKAxUhAxAIHTlmN2kQM3oECGMQAA"));
                lista.add(new ImaginiInsula("Insula Phuket", imagini.get(3), "https://www.google.com/imgres?q=insula%20phuket&imgurl=https%3A%2F%2Fwww.karpaten.ro%2Fblog%2Fwp-content%2Fuploads%2F2023%2F09%2FPhuket-principalele-elemente-de-care-te-poti-bucura-in-aceasta-insula-fermecatoare-insula-James-Bong-cu-stanci-unice.jpg&imgrefurl=https%3A%2F%2Fwww.karpaten.ro%2Fblog%2Fphuket-paradisul-thailandez-ce-asteapta-sa-fie-descoperit-de-oamenii-de-pretutindeni%2F&docid=X2N_Wq7Kh0IIsM&tbnid=7ljhqOoeujde2M&vet=12ahUKEwj-vLTXpuaKAxU-QVUIHSA3IS0QM3oECBcQAA..i&w=900&h=600&hcb=2&ved=2ahUKEwj-vLTXpuaKAxU-QVUIHSA3IS0QM3oECBcQAA"));
                lista.add(new ImaginiInsula("Insula Bali", imagini.get(4), "https://www.google.com/imgres?q=insula%20bali&imgurl=https%3A%2F%2Fwww.aerocenter.ro%2FProductContentFileHandler%2F1000%2F1000%2Fcircuite-indonezia-24669.jpg&imgrefurl=https%3A%2F%2Fwww.aerocenter.ro%2Fasia%2Findonezia%2Fdescopera-doua-insule-de-vis-bali-si-lombok&docid=8WG8VQrISoEMfM&tbnid=ZnVkW23vkML-AM&vet=12ahUKEwick7j4puaKAxU-HRAIHbyhA-AQM3oECBgQAA..i&w=1000&h=662&hcb=2&ved=2ahUKEwick7j4puaKAxU-HRAIHbyhA-AQM3oECBgQAA"));


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