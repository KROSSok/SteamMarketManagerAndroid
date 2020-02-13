package task;


import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.steammarketmanager.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;


public class URLDataWriter extends AsyncTask<String, Void, String> {

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        MainActivity.progressBar.setVisibility(ProgressBar.VISIBLE);
        MainActivity.tvProgress.setVisibility(TextView.VISIBLE);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MainActivity.tvProgress.setText(50+"%");
        }

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
    protected void onPostExecute(String data) {
        MainActivity.progressBar.setVisibility(ProgressBar.INVISIBLE);
        MainActivity.tvProgress.setVisibility(TextView.INVISIBLE);
        super.onPostExecute(data);
    }
}
