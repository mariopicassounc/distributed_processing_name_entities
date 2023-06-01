package namedEntity.entities.PersonEnt;

import namedEntity.entities.Person;

public class LastName extends Person {
    private String canonicalForm;
    private String origin;
    
    public LastName(String name, String category, int frequency, int id, String canonicalForm, String origin) {
        super(name, category, frequency, id);
        this.canonicalForm = canonicalForm;
        this.origin = origin;
    }

    // Getter and setter methods for the above attributes
}
