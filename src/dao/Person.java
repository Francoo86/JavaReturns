package dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Person {
    @DatabaseField(id = true, allowGeneratedIdInsert = true)
    private int id;
    private String name;
    private char sex;

    public Person(int id, String name, char sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
