package task;


import android.content.Context;

import com.example.steammarketmanager.MainActivity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class JsonDataParser {

    private final JSONObject mFileJson;

    public JsonDataParser(Context context) throws Exception {

            String json = new String();
            try {
                InputStream is = context.getAssets().open("steam_url.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser.parse(json);
        JSONObject jsonFileParser = (JSONObject) object;
        this.mFileJson = jsonFileParser;
    }

    public String getStringObject(String key) {
        String url;
        url = (String) mFileJson.get(key);
        return url;
    }

    public static task.MarketItem getDataByKey(String data)throws Exception{
        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser.parse(data);
        JSONObject jParser = (JSONObject) object;
        
        Double lowest_price;
        Double median_price;

        String currency = (String) jParser.get("lowest_price");
        String[] arrOfPrice = currency.split("");
        currency = (arrOfPrice[currency.length()-1]);

        try {
            lowest_price = (new DecimalFormat("##,###.###"+currency)).parse((String) jParser.get("lowest_price")).doubleValue();
        }
        catch(Exception e) {
            String split[] = ((String) jParser.get("lowest_price")).split(",");
            lowest_price = Double.parseDouble(split[0]);
        }

        try {
            median_price = ((new DecimalFormat("##,###.###"+currency)).parse((String) jParser.get("median_price"))).doubleValue();
        }
        catch(Exception e) {
            String split[] = ((String) jParser.get("median_price")).split(",");
            median_price = Double.parseDouble(split[0]);
        }

        task.MarketItem item = new task.MarketItem(
                    (Boolean) jParser.get("success"),
                    lowest_price,
                    Integer.parseInt((String) jParser.get("volume")),
                    median_price,
                    currency
            );
        return item;
    }
}