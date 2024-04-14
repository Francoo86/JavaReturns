package Servidor.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

//TODO: Implement.
public class CurrencyService {
    public static String AVAILABLE_COMMAND = "SHOW_AVAILABLE";

    private HashMap<String, Double> currencies;
    private List<String> availableCurrencies;
    private void readCurrencyFile() {
        currencies = new HashMap<>();

        try{
            URL url = getClass().getResource("CLPCurrencies");
            File fileObj = new File(url.getPath());

            Scanner reader = new Scanner(fileObj);

            while(reader.hasNextLine()){
                String data = reader.nextLine();
                String[] splitted = data.split(":");
                if(splitted.length < 2) continue;

                String type = splitted[0].trim();
                Double currencyMul = Double.parseDouble(splitted[1].trim());

                currencies.put(type, currencyMul);
            }

            availableCurrencies = new ArrayList<>(currencies.keySet());
            System.out.println("Currency service was setup!");
        }
        catch (FileNotFoundException e) {
            System.out.println("Currencies file wasn't found.");
        }
    }

    public CurrencyService(){
        readCurrencyFile();
    }

    public double convertToCLP(String type, Double amount){
        Double mul = currencies.get(type);
        if(mul == null){
            return -1d;
        }

        return amount * mul;
    }

    public List<String> getAvailableCurrencies(){
        return availableCurrencies;
    }
}
