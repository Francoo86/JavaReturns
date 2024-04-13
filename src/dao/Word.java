package dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable
public class Word {
    @DatabaseField(generatedId = true)
    private int id;
    //needs to be unique pls
    @DatabaseField(unique = true)
    public String wordName;
    @ForeignCollectionField
    Collection<WordDefinition> definitions;

    //empty constructor for this guy
    public Word(){}

    public String getWordName() {
        return wordName;
    }

    public int getId() {
        return id;
    }

    public Collection<WordDefinition> getDefinitions() {
        return definitions;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }
}
