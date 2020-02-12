package com.example.steammarketmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import task.JsonDataParser;
import task.MarketItem;
import task.URLDataWriter;

public class MainActivity extends AppCompatActivity {

    MarketItem steamItem = new MarketItem(false, 0.0, 0, 0.0, "\u20ac");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button getURLButton = findViewById(R.id.getURLButton);
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
                    steamItem = JsonDataParser.getDataByKey(
                            URLDataWriter.getDataFromURL(
                                    jsonDataParser.getStringObject("url"))
                    );
                } catch (Exception e){
                    e.printStackTrace();
                }
                intent.putExtra("success", steamItem.getSuccess());
                intent.putExtra("lowest_price", steamItem.getLowest_price());
                intent.putExtra("volume", steamItem.getVolume());
                intent.putExtra("median_price", steamItem.getMedian_price());
                intent.putExtra("currency", steamItem.getCurrency());
                intent.putExtra("time", steamItem.getTime());
                v.getContext().startActivity(intent);
            }
        });
    }
}
