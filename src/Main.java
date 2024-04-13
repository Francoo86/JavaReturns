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
            Dao<Person, Integer> personDao = DaoManager.createDao(connSource, Person.class);

            TableUtils.createTable(connSource, Person.class);

            Person person = new Person();
            person.setName("Juanito Perez");
            person.setSex('m');
            personDao.create(person);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}