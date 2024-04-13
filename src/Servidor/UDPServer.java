package Servidor;

import Servidor.services.DictionaryService;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLException;

public class UDPServer {
    private DatagramSocket socket;
    private DictionaryService dictService;

    public UDPServer(String url, int port) {
        try {
            socket = new DatagramSocket(port);
            ConnectionSource source = new JdbcConnectionSource(url);

            dictService = new DictionaryService(source);
        }
        catch (SocketException e) {
            System.out.println("[SERVER] Socket: " + e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("[SERVER] SQL: " + e.getMessage());
        };
    }

    public void listenClients() {
        try {
            while (true) {
                byte[] buffer = new byte[1000];
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socket.receive(peticion);
                System.out.println("Mensaje recibido: " + new String(peticion.getData()));
                DatagramPacket respuesta = new DatagramPacket(peticion.getData(), peticion.getLength(), peticion.getAddress(), peticion.getPort());
                socket.send(respuesta);
            }
        }
        catch (IOException e) {
            System.out.println("[SERVER] Socket (IO): " + e.getMessage());
        }
    }

    public enum Services {
        SEARCH_WORD,
        ADD_MEANING,
        CHANGE_CURRENCY,
    }
}
