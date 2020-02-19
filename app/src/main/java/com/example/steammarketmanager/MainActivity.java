package com.example.steammarketmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button makeTimerRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("STEAM");
        setContentView(R.layout.activity_main);

        makeTimerRequestButton = findViewById(R.id.makeTimerRequestButton);
        makeTimerRequestButton.setOnClickListener(this);

        Button getURLButton = findViewById(R.id.getURLButton);
        getURLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView url = findViewById(R.id.url);
                JsonDataParser jsonDataParser = new JsonDataParser(MainActivity.this);
                url.setText(jsonDataParser.getStringObject("url"));
            }
        });

        Button makeRequestButton = findViewById(R.id.makeRequestButton);
        makeRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultActivity.class );
                intent.putExtra("item", getMarketItemData());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ResultActivity.class);
        intent.putExtra("item", getMarketItemData());
        intent.putExtra("result", true);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Boolean result = data.getBooleanExtra("result", false);
        if (result) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    makeTimerRequestButton.callOnClick();
                }
            }, 300);
        } else return;
    }

    public MarketItem getMarketItemData(){
        MarketItem steamItem;
        JsonDataParser jsonDataParser = new JsonDataParser(MainActivity.this);
        URLDataWriter urlDataWriter = new URLDataWriter();
        try {
            steamItem = JsonDataParser.getDataByKey(urlDataWriter.execute(jsonDataParser.getStringObject("url")).get());
        } catch (Exception e) {
            e.printStackTrace();
            steamItem = null;
        }
        return steamItem;
    }

    public class URLDataWriter extends AsyncTask<String, Void, String> {

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
