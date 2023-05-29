package namedEntity.entities.PersonEnt;

import namedEntity.entities.Person;

public class Name extends Person {
    private String canonicalForm;
    private String origin;
    private String alternativeForm;

    public Name(String name, String category, int frequency, int id, String canonicalForm, String origin, String alternativeForm) {
        super(name, category, frequency, id);
        this.canonicalForm = canonicalForm;
        this.origin = origin;
        this.alternativeForm = alternativeForm;
    }

    // Getter and setter methods for the above attributes
}