package Servidor.serializables;

import java.util.HashMap;

//vietnam flashbacks
public class CurrencyResponse {
    private String provider;
    private String warningUpgrade;
    private String terms;
    //This is only the one relevant for the project.
    private String currencyBase;
    private String requestedDate;
    private int lastUpdatedTime;
    //Also this one.
    private HashMap<String, Double> rates;

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public String getCurrencyBase() {
        return currencyBase;
    }

}
