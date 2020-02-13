package com.example.steammarketmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import task.JsonDataParser;
import task.MarketItem;
import task.URLDataWriter;

public class MainActivity extends AppCompatActivity {

    MarketItem steamItem = new MarketItem(false, 0.0, 0, 0.0, "\u20ac");
    public static ProgressBar progressBar;
    public static TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("STEAM");
        setContentView(R.layout.activity_main);
        Button getURLButton = findViewById(R.id.getURLButton);
        progressBar = findViewById(R.id.makeRequestProgressBar);
        tvProgress = findViewById(R.id.tv_makeRequestProgressBar);

        getURLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView url = findViewById(R.id.url);
                try {
                    JsonDataParser jsonDataParser = new JsonDataParser(MainActivity.this);
                    String urlFromJson = jsonDataParser.getStringObject("url");
                    url.setText(urlFromJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button makeRequestButton = findViewById(R.id.makeRequestButton);
        makeRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultActivity.class );
                try {
                    JsonDataParser jsonDataParser = new JsonDataParser(MainActivity.this);
                    String url = jsonDataParser.getStringObject("url");
                    URLDataWriter urlDataWriter = new URLDataWriter();
                    String data = urlDataWriter.execute(url).get();
                    steamItem = JsonDataParser.getDataByKey(data);
                } catch (Exception e){
                    e.printStackTrace();
                }
                intent.putExtra("item", steamItem);
                v.getContext().startActivity(intent);
            }
        });
    }
}
