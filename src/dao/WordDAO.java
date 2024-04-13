package dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class WordDAO implements IEntityDAO<Word, String> {
    public static final int MAX_DEFINITIONS = 5;
    private Dao<Word, String> wordManager;
    private QueryBuilder<Word, String> queryBuilder;
    private MeaningDAO defManager;

    public WordDAO(ConnectionSource connSource) {
        try {
            defManager = new MeaningDAO(connSource);

            //This better.
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

    public void addMeaning(String currentWord, String meaning) {
        try {
            Word wordObj = findUnique(currentWord);
            if(wordObj == null) {
                return;
            }

            Collection<WordDefinition> actualDefs = wordObj.getDefinitions();

            if(actualDefs.size() >= MAX_DEFINITIONS) {
                throw new IOException(String.format("WordDAO: A word can't have more than %s definitions.", MAX_DEFINITIONS));
            }

            WordDefinition def = new WordDefinition();
            def.setWord(wordObj);
            def.setDef(meaning);
            defManager.add(def);
        }catch (IOException e) {
            System.out.println(e);
        }
    }

    public Collection<WordDefinition> getMeanings(String wordName) {
        Word word = findUnique(wordName);
        if(word == null) return null;

        return word.getDefinitions();
    }
}
