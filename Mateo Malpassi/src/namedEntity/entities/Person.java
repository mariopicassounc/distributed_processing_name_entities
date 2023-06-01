package namedEntity.entities;
import java.util.List;

import namedEntity.NamedEntity;
import namedEntity.Theme;

public class Person extends NamedEntity {
    int id;
    
    // Constructor
    public Person(String name, List<String> categoryList, int frequency, Theme theme, int id) {
        super(name, categoryList, frequency, theme);
        this.id = id;
    }

    // Getter and setter methods for the id attribute
}