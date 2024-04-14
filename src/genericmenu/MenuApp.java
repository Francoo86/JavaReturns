package genericmenu;

import Servidor.UDPServer.Services;
import cliente.UDPClient;
import shd_utils.ParseHelpers;

import java.util.Scanner;

public class MenuApp {
    private static final Services[] SERVICES = Services.values();
    private static final int STOP_CODE = SERVICES.length;
    private Scanner sc;

    private UDPClient client;

    //ONLY FOR TESTING PURPOSES.
    public MenuApp() {
        client = new UDPClient();
        sc = new Scanner(System.in);
    }

    private void displayOptions() {
        System.out.println("/******** MENU OPCIONES ***********/");
        System.out.println("1. Buscar una palabra en el diccionario.");
        System.out.println("2. Agregar una palabra al diccionario.");
        System.out.println("3. Cambiar moneda.");
        System.out.println("4. Cerrar programa.");
    }

    private void prepareWordSearch() {
        String word = sc.next().trim();

        if(!ParseHelpers.isValidWord(word)) {
            System.out.println("La palabra tiene que ser mayor a 3 caracteres.");
            return;
        }

        String content = ParseHelpers.createContents(Services.SEARCH_WORD, word);
        client.sendMessage(content);
    }

    private void prepareWordAdding() {
        System.out.println("Introduzca la palabra a colocar significado.");
        String word = sc.nextLine().trim();
        System.out.println("Introduzca el signficado correspondiente.");
        String meaning = sc.nextLine().trim();

        if(!ParseHelpers.isValidWord(word) || !ParseHelpers.isValidWord(meaning)) {
            System.out.println("No se permiten definiciones vacias o palabras muy pequeñas (menores a 3 caracteres).");
            return;
        }

        String content = ParseHelpers.createContents(Services.ADD_MEANING, word, meaning);
        client.sendMessage(content);
    }

    //you can't return onto while true without breaking it.
    //returns true to stop.
    private boolean doOptions() {
        int input = sc.nextInt();
        // System.out.printf("The selected input was: %s\n", input);
        if(input <= 0 || input > SERVICES.length) {
            return false;
        }

        //goofy ahh reset nextline.
        sc.nextLine();

        Services service = SERVICES[input - 1];

        System.out.println("Servicio actual: " + service.name());

        switch (service){
            case SEARCH_WORD:
                prepareWordSearch();
                break;
            case ADD_MEANING:
                prepareWordAdding();
                break;
            case CHANGE_CURRENCY:
                break;
                //TODO: Implement.
            //HACK: Add this for avoiding thinking too much.
            case NULL_SERVICE:
                System.out.println("Saliendo del menu...");
                return true;
        }

        return false;
    }

    public void runMenu() {
        //crear el scanner.
        while(true) {
            displayOptions();
            if (doOptions()){
                sc.close();
                break;
            };
        }
    }

    public static enum MenuStatus {
        NONE,
        STOP,
        PROCEED,
    }
}
