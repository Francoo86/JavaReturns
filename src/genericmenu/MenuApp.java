package genericmenu;

import Servidor.UDPServer.Services;
import cliente.UDPClient;

import java.util.Scanner;

public class MenuApp {
    private static final Services[] SERVICES = Services.values();
    private static final int STOP_CODE = SERVICES.length;

    private UDPClient client;

    //ONLY FOR TESTING PURPOSES.
    public MenuApp() {
        client = new UDPClient();
    }

    private void displayOptions() {
        System.out.println("/******** MENU OPCIONES ***********/");
        System.out.println("1. Buscar una palabra en el diccionario.");
        System.out.println("2. Agregar una palabra al diccionario.");
        System.out.println("3. Cambiar moneda.");
        System.out.println("4. Cerrar programa.");
    }

    //you can't return onto while true without breaking it.
    //returns true to stop.
    private boolean doOptions(Scanner sc) {
        int input = sc.nextInt();
        System.out.printf("The selected input was: %s\n", input);
        if(input <= 0 || input > SERVICES.length) {
            return false;
        }

        Services service = SERVICES[input - 1];

        switch (service){
            case SEARCH_WORD:
            case ADD_MEANING:
            case CHANGE_CURRENCY:
            case NULL_SERVICE:
                System.out.println("Saliendo del menu...");
                return true;
        }

        return false;
    }

    public void runMenu() {
        //crear el scanner.
        Scanner sc = new Scanner(System.in);
        while(true) {
            displayOptions();
            if (doOptions(sc)){
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
