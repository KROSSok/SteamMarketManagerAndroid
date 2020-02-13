package com.example.steammarketmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;
import task.JsonDataParser;
import task.MarketItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("STEAM");
        setContentView(R.layout.activity_main);

        final JsonDataParser jsonDataParser = new JsonDataParser(MainActivity.this);
        final String urlFromJson = jsonDataParser.getStringObject("url");

        Button getURLButton = findViewById(R.id.getURLButton);
        getURLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView url = findViewById(R.id.url);
                url.setText(urlFromJson);
            }
        });

        Button makeRequestButton = findViewById(R.id.makeRequestButton);
        makeRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultActivity.class );
                MarketItem steamItem;
                URLDataWriter urlDataWriter = new URLDataWriter();
                try {
                    steamItem = JsonDataParser.getDataByKey(urlDataWriter.execute(jsonDataParser.getStringObject("url")).get());
                } catch (Exception e) {
                    e.printStackTrace();
                    steamItem = null;
                }
                intent.putExtra("item", steamItem);
                v.getContext().startActivity(intent);
            }
        });
    }

    class URLDataWriter extends AsyncTask<String, Void, String> {

        ProgressBar progressBar = findViewById(R.id.makeRequestProgressBar);
        TextView tvProgress = findViewById(R.id.tv_makeRequestProgressBar);

        @Override
        protected String doInBackground(String... urls) {
            publishProgress();
            String data = null;
            for (String url : urls) {
                try {
                    String urlData;
                    URLConnection urlConnection = new URL(url).openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    urlData = in.readLine();
                    data = urlData;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(ProgressBar.VISIBLE);
            tvProgress.setVisibility(TextView.VISIBLE);
            tvProgress.setText(50+"%");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String data) {
            tvProgress.setText(100+"%");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(ProgressBar.GONE);
            tvProgress.setVisibility(TextView.GONE);
            super.onPostExecute(data);
        }
    }

}
