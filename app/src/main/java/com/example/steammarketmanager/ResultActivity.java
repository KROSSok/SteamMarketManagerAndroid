package com.example.steammarketmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import task.MarketItem;

public class ResultActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("RESULTS");
        setContentView(R.layout.activity_result);

        MarketItem marketItem = (MarketItem)getIntent().getSerializableExtra("item");

        TextView successItem = findViewById(R.id.success);
        successItem.setText(marketItem.getSuccess().toString());

        TextView lowestPriceItem = findViewById(R.id.lowestPrice);
        lowestPriceItem.setText(marketItem.getLowest_price().toString());

        TextView volumeItem = findViewById(R.id.volume);
        volumeItem.setText(""+marketItem.getVolume());

        TextView medianPriceItem = findViewById(R.id.medianPrice);
        medianPriceItem.setText(marketItem.getMedian_price().toString());

        TextView currencyItem = findViewById(R.id.currency);
        currencyItem.setText(marketItem.getCurrency());

        TextView timeItem = findViewById(R.id.time);
        timeItem.setText(marketItem.getTime());

    }
}
