package namedEntity.entities.PersonEnt;

import namedEntity.entities.Person;
import namedEntity.Theme;

import java.util.List;

public class LastName extends Person {
    private String canonicalForm;
    private String origin;
    
    public LastName(String name, List<String> category, int frequency, Theme theme, int id, String canonicalForm, String origin) {
        super(name, category, frequency, theme, id);
        this.canonicalForm = canonicalForm;
        this.origin = origin;
    }

    // Getter and setter methods for the above attributes
}
