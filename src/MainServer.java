import Servidor.UDPServer;
import genericmenu.MenuApp;

public class MainServer {
    private static int PORT = 6789;
    public static void main(String[] args) {
        UDPServer server = new UDPServer("jdbc:sqlite:sample.db", PORT);
        server.listenClients();
    }
}
