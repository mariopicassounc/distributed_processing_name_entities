package namedEntity.entities;
import namedEntity.NamedEntity;

public class Person extends NamedEntity {
    int id;
    
    // Constructor
    public Person(String name, String category, int frequency, int id) {
        super(name, category, frequency);
        this.id = id;
    }

    // Getter and setter methods for the id attribute
}