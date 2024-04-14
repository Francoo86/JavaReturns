import Servidor.services.DictionaryService;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import genericmenu.MenuApp;

import java.sql.SQLException;

public class MainClient {
    public static void main(String[] args) {
        MenuApp menu = new MenuApp();
        menu.runMenu();
    }

    private static void testPersonDao() {
        String dbUrl = "jdbc:sqlite:sample.db";
        try {
            ConnectionSource connSource = new JdbcConnectionSource(dbUrl);
            DictionaryService dictService = new DictionaryService(connSource);
            //dictService.addWord("porotos");
            dictService.addDefinition("porotos", "prueba 5to significado");
            dictService.addDefinition("porotos", "hola si");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}