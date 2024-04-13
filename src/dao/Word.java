package dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable
public class Word {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    public String wordName;
    @DatabaseField
    public String definition;
    @ForeignCollectionField
    Collection<WordDefinition> definitions;

    //empty constructor for this guy
    Word(){}

    public String getDefinition() {
        return definition;
    }

    public String getWordName() {
        return wordName;
    }

    public int getId() {
        return id;
    }

    public Collection<WordDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }
}
