import Servidor.services.DictionaryService;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dao.Person;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        testPersonDao();
        System.out.println("Hello world!");
    }

    private static void testPersonDao() {
        String dbUrl = "jdbc:sqlite:sample.db";
        try {
            ConnectionSource connSource = new JdbcConnectionSource(dbUrl);
            DictionaryService dictService = new DictionaryService(connSource);
            dictService.addWord("porotos");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}