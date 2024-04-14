package Servidor;

import Servidor.services.CurrencyService;
import Servidor.services.DictionaryService;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import dao.Word;
import dao.WordDefinition;
import shd_utils.ParseHelpers;
import shd_utils.Services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class UDPServer {
    private DatagramSocket socket;
    //setup services.
    private DictionaryService dictService;
    private CurrencyService currencyService;

    public static final int MAX_BYTES = 1000;
    //inmutable ahh array.
    private static final Services[] services = Services.values();

    private Services getService(String num) {
        int castNum = Integer.parseInt(num);
        return services[castNum];
    }

    public UDPServer(String url, int port) {
        try {
            socket = new DatagramSocket(port);
            ConnectionSource source = new JdbcConnectionSource(url);

            dictService = new DictionaryService(source);
            currencyService = new CurrencyService();

            System.out.printf("Setting up server at port %s.\n", port);
        }
        catch (SocketException e) {
            System.out.println("[SERVER] Socket: " + e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("[SERVER] SQL: " + e.getMessage());
        }
    }

    //TODO: Move this functionality.
    public String formatLookupWordResp(String word){
        Word tempWord = dictService.lookupWord(word);
        if(tempWord == null || tempWord.getDefinitions().isEmpty()) {
            return "NO_DEF";
        }

        StringBuilder sb = new StringBuilder();
        List<WordDefinition> defs = tempWord.getDefinitions();

        for(int i = 0; i < defs.size(); i++) {
            WordDefinition def = defs.get(i);
            String fullString = String.format("%s. %s\n", i + 1, def.getDef());
            sb.append(fullString);
        }

        return sb.toString();
    }

    public String formatAddDictionary(String word, String meaning) {
        boolean check = dictService.addDefinition(word, meaning);
        return String.format("El significado de %s%s fue añadido.", word, check ? "" : " no");
    }

    public String formatCurrencyResponse(List<String> contents){
        //Mostrar monedas disponibles al usuario.
        if (contents.get(0).equals(CurrencyService.AVAILABLE_COMMAND)){
            Set<String> currencies = currencyService.getAvailableCurrencies();
            StringBuilder sb = new StringBuilder();

            sb.append("Monedas disponibles:\n[");

            int i = 0;

            for(String currency : currencies){
                sb.append(currency);
                if (i < currencies.size() - 1) {
                    sb.append(", ");
                }
                i++;
            }

            sb.append("]\n");
            return sb.toString();
        }

        String type = contents.get(0).toUpperCase();
        double amount = Double.parseDouble(contents.get(1));
        double converted = currencyService.convertToCLP(type, amount);

        if(converted == -1d) {
            return String.format("De momento la moneda %s no se encuentra disponible.", type);
        }

        return String.format("%s en %s en CLP equivale a $%s", amount, type, converted);
    }

    public String handleServices(List<String> contents) {
        Services serv = getService(contents.get(0));

        contents.remove(0);

        return switch (serv) {
            case SEARCH_WORD -> formatLookupWordResp(contents.get(0));
            case ADD_MEANING -> formatAddDictionary(contents.get(0), contents.get(1));
            case CHANGE_CURRENCY -> formatCurrencyResponse(contents);
            default -> "NOT_IMPLEMENTED";
        };

    }

    public void listenClients() {
        System.out.println("SERVER: Listening to clients.");
        try {
            while (true) {
                byte[] buffer = new byte[MAX_BYTES];
                //Escuchar clientes.
                DatagramPacket req = new DatagramPacket(buffer, MAX_BYTES);
                socket.receive(req);

                //Mensaje recibido.
                String receivedMessage = new String(req.getData());
                List<String> contents = ParseHelpers.parseContents(receivedMessage);
                String serviceResponse = handleServices(contents);

                System.out.println("Data: " + serviceResponse);
                System.out.println("Response size: " + serviceResponse.length());
                DatagramPacket resp = new DatagramPacket(serviceResponse.getBytes(), serviceResponse.length(), req.getAddress(), req.getPort());
                socket.send(resp);
            }
        }
        catch (IOException e) {
            System.out.println("[SERVER] Socket (IO): " + e.getMessage());
        }
    }
}
