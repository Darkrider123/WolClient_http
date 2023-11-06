package com.example.wolclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    WolClient wolClient = new WolClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button status = findViewById(R.id.status);
        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        Button restart = findViewById(R.id.restart);


        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = getServerStatus();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = startServer();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String message = stopServer();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = restartServer();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getServerStatus() {

        NetworkWorker networkWorker = new NetworkWorker() {
            @Override
            public void run() {
                try {
                    message = wolClient.getServerStatus();
                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Nu am reusit sa fac conexiunea";
                }
            }
        };

        startSeparateThreadToDoDirtyWorkAndWait(networkWorker);
        return networkWorker.getMessage();
    }

    private String startServer() {
        NetworkWorker networkWorker = new NetworkWorker() {
            @Override
            public void run() {
                try {
                    message = wolClient.startServer();
                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Nu am reusit sa comunic pornirea serverului";
                }
            }
        };

        startSeparateThreadToDoDirtyWorkAndWait(networkWorker);
        return networkWorker.getMessage();
    }

    private String stopServer() {
        NetworkWorker networkWorker = new NetworkWorker() {
            @Override
            public void run() {
                try {
                    message = wolClient.stopServer();
                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Nu am reusit sa comunic oprirea serverului";
                }
            }
        };

        startSeparateThreadToDoDirtyWorkAndWait(networkWorker);
        return networkWorker.getMessage();
    }

    private String restartServer() {
        NetworkWorker networkWorker = new NetworkWorker() {
            @Override
            public void run() {
                try {
                    message = wolClient.restartServer();
                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Nu am reusit sa comunic restartul serverului";
                }
            }
        };

        startSeparateThreadToDoDirtyWorkAndWait(networkWorker);
        return networkWorker.getMessage();
    }

    private void startSeparateThreadToDoDirtyWorkAndWait(NetworkWorker networkWorker) {
        Thread thread = new Thread(networkWorker);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            Toast.makeText(getApplicationContext(), "Thread intrerupt din motive necunoscute!", Toast.LENGTH_SHORT).show();
        }
    }

}