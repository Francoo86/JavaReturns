package server;

import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ServidorUDP {
    public static void main(String args[]) {
        // args[0] puerto del servidor
        try {
            DatagramSocket unSocket = new DatagramSocket(6789);
            System.out.println("Servidor iniciado en el puerto 6789");
            connectDB();
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

    //method for db connection
    public static void connectDB(){
        Connection connection = null;
    
        try{
            connection = DriverManager.getConnection( "jdbc:sqlite:sample.db" );
            Statement statement = connection.createStatement();
            if ( connection != null ){
                System.out.println("Conexión exitosa!");
                statement.setQueryTimeout(30);  // set timeout to 30 sec.
                statement.execute("INSERT into PERSON (id, name, sex) VALUES (2, 'el pepe', 'm')");
                //statement.executeUpdate("drop table if exists person");
                //statement.executeUpdate("create table person (id integer, name VARCHAR, sexo string)");
            }
        }
        catch ( Exception ex ) {
            System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
            System.out.println("Error en la conexión");
        }
    }
}