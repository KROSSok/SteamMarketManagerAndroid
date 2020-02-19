package com.example.steammarketmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import task.MarketItem;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("RESULTS");
        setContentView(R.layout.activity_result);
            MarketItem marketItem = (MarketItem)getIntent().getSerializableExtra("item");
            if(marketItem != null) {
                TextView successItem = findViewById(R.id.success);
                successItem.setText(marketItem.getSuccess().toString());

                TextView lowestPriceItem = findViewById(R.id.lowestPrice);
                lowestPriceItem.setText(marketItem.getLowest_price().toString());

                TextView volumeItem = findViewById(R.id.volume);
                volumeItem.setText("" + marketItem.getVolume());

                TextView medianPriceItem = findViewById(R.id.medianPrice);
                medianPriceItem.setText(marketItem.getMedian_price().toString());

                TextView currencyItem = findViewById(R.id.currency);
                currencyItem.setText(marketItem.getCurrency());

                TextView timeItem = findViewById(R.id.time);
                timeItem.setText(marketItem.getTime());
            } else {
                TextView dataException = findViewById(R.id.dataException);
                dataException.setText("no data to show");
            }
            reverseTimer(13, getIntent().getBooleanExtra("result", false));
        }

        TextView timer;
        Button cancelButton;

        public void reverseTimer (int Seconds, final Boolean isTimerRequest){

        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (isTimerRequest) {
                    timer = findViewById(R.id.timerProgress);
                    timer.setVisibility(TextView.VISIBLE);
                    cancelButton = findViewById(R.id.cancelTimerButton);
                    cancelButton.setVisibility(Button.VISIBLE);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timer.setVisibility(TextView.INVISIBLE);
                            cancel();
                        }
                    });
                    int seconds = (int) (millisUntilFinished / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;
                    timer.setText("TIME : " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                } else {
                    cancel();
                }
            }
            public void onFinish() {
                Intent intent = new Intent();
                intent.putExtra("result", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        }.start();
    }

}

