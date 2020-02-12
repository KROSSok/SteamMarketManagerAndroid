package com.example.steammarketmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.stream.Stream;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView successItem = findViewById(R.id.success);
        Boolean success = getIntent().getBooleanExtra("success", false);
        successItem.setText(success.toString());

        TextView lowestPriceItem = findViewById(R.id.lowestPrice);
        Double lowestPrice = getIntent().getDoubleExtra("lowest_price", 0.0);
        lowestPriceItem.setText(lowestPrice.toString());

        TextView volumeItem = findViewById(R.id.volume);
        int volume = getIntent().getIntExtra("volume", 0);
        volumeItem.setText(""+volume);

        TextView medianPriceItem = findViewById(R.id.medianPrice);
        Double medianPrice = getIntent().getDoubleExtra("median_price", 0.0);
        medianPriceItem.setText(medianPrice.toString());

        TextView currencyItem = findViewById(R.id.currency);
        String currency = getIntent().getStringExtra("currency");
        currencyItem.setText(currency);

        TextView timeItem = findViewById(R.id.time);
        String time = getIntent().getStringExtra("time");
        timeItem.setText(time);

    }
}
