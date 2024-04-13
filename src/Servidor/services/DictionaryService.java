package Servidor.services;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dao.Word;
import com.j256.ormlite.dao.Dao;
import dao.WordDefinition;

import java.sql.SQLException;

public class DictionaryService {
    private Dao<Word, Integer> wordDao;

    //inject this connection because it looks better.
    public DictionaryService(ConnectionSource connSource) {
        //this.connSource = connSource;
        try {
            this.wordDao = DaoManager.createDao(connSource, Word.class);
            //Setup this service if first time.
            TableUtils.createTableIfNotExists(connSource, Word.class);
            TableUtils.createTableIfNotExists(connSource, WordDefinition.class);
        }
        catch(SQLException e) {
            System.out.printf("DictionaryService: Can't initialize service because: %s", e);
        }
    }

    public void addWord(String word) {
        try {
            Word newWord = new Word();
            newWord.setWordName(word);
            wordDao.create(newWord);
        }
        catch (SQLException e) {
            System.out.printf("SQL: Can't add Word %s to dictionary because: %s", word, e);
        }
    }

    public void addDefinition(String word, String def) {

    }

    public void searchWord() {}
}
