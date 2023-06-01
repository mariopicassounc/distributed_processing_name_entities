package namedEntity.entities;
import java.util.List;

import namedEntity.NamedEntity;

public class Person extends NamedEntity {
    int id;
    
    // Constructor
    public Person(String name, List<String> categoryList, int frequency, int id) {
        super(name, categoryList, frequency);
        this.id = id;
    }

    // Getter and setter methods for the id attribute
}