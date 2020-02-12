package task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLDataWriter {
    public static String getDataFromURL(String url) throws Exception {
        String data;
        URLConnection urlConnection = new URL(url).openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        data=in.readLine();
        return data;
    }
}
