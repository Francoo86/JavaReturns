package Servidor.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

//TODO: Implement.
public class CurrencyService {
    private HashMap<String, Double> currencies;
    private void readCurrencyFile() {
        currencies = new HashMap<>();

        try{
            File fileObj = new File("CLPCurrencies.txt");
            Scanner reader = new Scanner(fileObj);

            while(reader.hasNextLine()){
                String data = reader.nextLine();
                String[] splitted = data.split(":");
                if(splitted.length < 2) continue;

                String type = splitted[0].trim();
                Double currencyMul = Double.parseDouble(splitted[1].trim());

                currencies.put(type, currencyMul);
            }
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
}
