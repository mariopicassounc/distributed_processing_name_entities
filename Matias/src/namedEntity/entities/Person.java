package namedEntity.entities;
import namedEntity.NamedEntity;
import namedEntity.Theme;


import java.util.List;

public class Person extends NamedEntity {
    int id;
    
    // Constructor
    public Person(String name, List<String> category, int frequency, Theme theme, int id) {
        super(name, category, frequency, theme);
        this.id = id;
    }

    // Getter and setter methods for the id attribute
}