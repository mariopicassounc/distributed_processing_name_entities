package namedEntity.entities.PersonEnt;

import namedEntity.entities.Person;

public class Title extends Person {
    private String canonicalForm;
    private String profession;

    public Title(String name, String category, int frequency, int id, String canonicalForm, String profession) {
        super(name, category, frequency, id);
        this.canonicalForm = canonicalForm;
        this.profession = profession;
    }

    // Getter and setter methods for the above attributes
}