package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
 
public class prueba {
 
   public static void main ( String args[] ){
 
      Connection connection = null;
 
      try{
         connection = DriverManager.getConnection( "jdbc:sqlite:sample.db" );
         Statement statement = connection.createStatement();
         if ( connection != null ){
            System.out.println("Conexión exitosa!");
         }
      }
      catch ( Exception ex ) {
         System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
         System.out.println("Error en la conexión");
      }
   }
 
}