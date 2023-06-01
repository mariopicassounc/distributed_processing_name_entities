package namedEntity.entities.PersonEnt;

import java.util.List;

import namedEntity.entities.Person;

public class LastName extends Person {
    private String canonicalForm;
    private String origin;
    
    public LastName(String name, List<String> categoryList, int frequency, int id, String canonicalForm, String origin) {
        super(name, categoryList, frequency, id);
        this.canonicalForm = canonicalForm;
        this.origin = origin;
    }

    // Getter and setter methods for the above attributes
}
