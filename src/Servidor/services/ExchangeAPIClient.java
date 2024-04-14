package Servidor.services;

import Servidor.serializables.CurrencyResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//Limit this guy to ExchangeRATE
public class ExchangeAPIClient {
    public static String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private static int SUCCESS_CODE = 200;

    private CurrencyResponse connectData(String type){
        try {
            URL url = new URL("https://api.exchangerate-api.com/v4/latest/" + type.toLowerCase());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != SUCCESS_CODE) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            };

            //try to read this json ahh thing.
            try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                String output = sb.toString();
                return new Gson().fromJson(output, CurrencyResponse.class);
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    public CurrencyResponse getCurrencyData(String type){
        return connectData(type);
    };
}
