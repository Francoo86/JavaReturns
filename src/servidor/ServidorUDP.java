import java.net.*;
import java.io.*;

public class ServidorUDP {
    public static void main(String args[]) {
        // args[0] puerto del servidor
        try {
            DatagramSocket unSocket = new DatagramSocket(6789);
            System.out.println("Servidor iniciado en el puerto 6789");
            while (true) {
                byte[] buffer = new byte[1000];
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                unSocket.receive(peticion);
                System.out.println("Mensaje recibido: " + new String(peticion.getData()));
                DatagramPacket respuesta = new DatagramPacket(peticion.getData(), peticion.getLength(), peticion.getAddress(), peticion.getPort());
                unSocket.send(respuesta);
            }
        } catch (SocketException e) { System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) { System.out.println("IO: " + e.getMessage()); }
    }
}