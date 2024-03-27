package com.example.dz_26_03;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button getButton;

    OkHttpClient client;


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

        String url = "https://publicobject.com/helloworld.txt";
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        getButton = findViewById(R.id.getButton);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread newThread = new Thread(){
                    @Override
                    public void run() {
                        try (Response response = client.newCall(request).execute()) {
                            if (!response.isSuccessful()) {
                                throw new IOException("Запрос к серверу не был успешен: " +
                                        response.code() + " " + response.message());
                            }
                            // пример получения конкретного заголовка ответа
                            Log.d("test",response.toString());
                            // вывод тела ответа
                            Log.d("test",response.body().string());
                        } catch (IOException e) {
                            Log.d("test","Ошибка подключения: " + e);
                        }
                    }
                };



                newThread.start();
            }

        });

    }
}