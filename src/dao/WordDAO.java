package dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class WordDAO implements IEntityDAO<Word, String> {
    private Dao<Word, String> wordManager;
    private QueryBuilder<Word, String> queryBuilder;

    public WordDAO(ConnectionSource connSource) {
        try {
            wordManager = DaoManager.createDao(connSource, Word.class);
            queryBuilder = wordManager.queryBuilder();

            TableUtils.createTableIfNotExists(connSource, Word.class);
        }
        catch(SQLException e) {
            System.out.printf("WordDAO: Can't initialize DAO because: %s", e);
        }
    }

    @Override
    public void add(Word entity) {
        try{
            wordManager.create(entity);
        }
        catch (SQLException e) {
            System.out.printf("WordDAO: Can't add word because: %s", e);
        }
    };

    @Override
    public Word findByWordName(String wordName) {
        return null;
    }

    @Override
    public List findAll() {
        try {
            List<Word> allWords = wordManager.queryForAll();
            return allWords;
        }
        catch (SQLException e) {
            System.out.printf("Can't get all elements of WordDAO because: %s", e);
        }

        return null;
    }

    @Override
    public Word findUnique(String wordName) {
        try {
            Word tempWord = queryBuilder.where().eq("wordName", wordName).queryForFirst();
            return tempWord;
        }
        catch(SQLException e) {
            System.out.printf("WordDAO: Can't find element %s because %s", wordName, e);
        }

        return null;
    };
}
