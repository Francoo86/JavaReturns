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

public class MeaningDAO implements IEntityDAO<WordDefinition, String> {
    private Dao<WordDefinition, String> meaningManager;
    private QueryBuilder<WordDefinition, String> queryBuilder;

    public MeaningDAO(ConnectionSource connSource) {
        try {
            meaningManager = DaoManager.createDao(connSource, WordDefinition.class);
            queryBuilder = meaningManager.queryBuilder();

            TableUtils.createTableIfNotExists(connSource, WordDefinition.class);
        }
        catch(SQLException e) {
            System.out.printf("WordDAO: Can't initialize DAO because: %s", e);
        }
    }

    @Override
    public void add(WordDefinition entity) {
        try{
            Word word = entity.getWord();
            if(word == null) {
                throw new IOException("MeaningDAO: The definition needs to have an associated word.");
            }

            meaningManager.create(entity);
        }
        catch (SQLException e) {
            System.out.printf("MeaningDAO: Can't add definition for %s because %s", entity.getWord().getWordName(), entity)
        }
        catch (IOException e) {
            System.out.printf("MeaningDAO: Needs to have a word to be added.");
        }
    }

    @Override
    public WordDefinition findByWordName(String wordName) {
        return null;
    }

    @Override
    public Collection<WordDefinition> findAll() {
        return List.of();
    }

    @Override
    public WordDefinition findUnique(String criteria) {
        try {

        }
        catch (SQLException e) {
            System.out.printf("xdddd");
        }
    }
}
