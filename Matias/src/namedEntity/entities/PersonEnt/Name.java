package namedEntity.entities.PersonEnt;

import namedEntity.entities.Person;
import namedEntity.Theme;

import java.util.List;

public class Name extends Person {
    private String canonicalForm;
    private String origin;
    private String alternativeForm;

    public Name(String name, List<String> category, int frequency, Theme theme, int id, String canonicalForm, String origin, String alternativeForm) {
        super(name, category, frequency, theme, id);
        this.canonicalForm = canonicalForm;
        this.origin = origin;
        this.alternativeForm = alternativeForm;
    }

    // Getter and setter methods for the above attributes
}
