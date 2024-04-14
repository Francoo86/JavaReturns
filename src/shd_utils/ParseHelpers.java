package shd_utils;

import Servidor.UDPServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseHelpers {
    public static final int REQUIRED_WORD_SIZE = 3;
    public static String clearWord(String word) {
        return word.trim();
    }

    public static String createContents(UDPServer.Services serv, String ... contents){
        StringBuilder sb = new StringBuilder();
        sb.append(serv.ordinal() + ":[");

        for(int i = 0; i < contents.length; i++) {
            String content = contents[i];
            sb.append(content);
            if (i < contents.length - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    };

    //this is for the server...
    public static Collection<String> parseContents(String content){
        // Define a regular expression pattern to match the integer and the list of strings
        // Regular expression pattern to match the number and strings inside brackets
        Pattern pattern = Pattern.compile("(\\d+):\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(content);

        if(!matcher.find()) {
            return null;
        }

        List<String> parsedContents = new ArrayList<>();

        // Extracting the number
        String number = matcher.group(1);
        System.out.println("Numero del servicio: " + number);

        parsedContents.add(number);

        // Extracting the strings inside brackets
        String[] strings = matcher.group(2).split(",\\s*");
        for (String str : strings) {
            parsedContents.add(str);
        }

        return parsedContents;
    }

    public static boolean isValidWord(String word) {
        if(word.length() < REQUIRED_WORD_SIZE) {
            //System.out.printf("Las palabras deberían tener al menos %s caracteres de longitud.", REQUIRED_WORD_SIZE);
            return false;
        }

        return true;
    }
}
