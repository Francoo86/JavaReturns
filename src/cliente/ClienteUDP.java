import java.net.*;
import java.io.*;

public class ClienteUDP {
    static final String HOSTNAME = "localhost";
    static final String TEST_MESSAGE = "hola a todos";
    public static void main(String args[]) {
        try {
            DatagramSocket unSocket = new DatagramSocket();
            unSocket.setSoTimeout(5000); // Establecer tiempo de espera de 5 segundos
            byte [] m = TEST_MESSAGE.getBytes();
            System.out.println(m);
            InetAddress unHost = InetAddress.getByName(HOSTNAME);
            int puertoServidor = 6789;
            DatagramPacket peticion = new DatagramPacket(m, TEST_MESSAGE.length(), unHost, puertoServidor);
            unSocket.send(peticion);
            byte[] buffer = new byte[1000];
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
            unSocket.receive(respuesta);
            System.out.println("Respuesta: " + new String(respuesta.getData()));
            unSocket.close();
        } catch (SocketException e) { 
            System.out.println("Socket: " + e.getMessage());
        } catch (SocketTimeoutException e) { 
            System.out.println("Tiempo de espera agotado: " + e.getMessage());
        } catch (IOException e) { 
            System.out.println("IO: " + e.getMessage());
        }
    }
}