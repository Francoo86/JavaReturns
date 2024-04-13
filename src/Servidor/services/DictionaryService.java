package Servidor.services;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dao.Word;
import com.j256.ormlite.dao.Dao;
import dao.WordDAO;
import dao.WordDefinition;

import java.sql.SQLException;

public class DictionaryService {
    private WordDAO wordDao;

    //inject this connection because it looks better.
    public DictionaryService(ConnectionSource connSource) {
        wordDao = new WordDAO(connSource);
    }

    public void addWord(String word) {
        Word newWord = new Word();
        newWord.setWordName(word);
        wordDao.add(newWord);
    }

    public void addDefinition(String word, String def) {
        wordDao.addMeaning(word, def);
    }

    public Word lookupWord(String wordName) {
        return wordDao.findUnique(wordName);
    }
}
