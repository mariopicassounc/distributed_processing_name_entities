package namedEntity.entities.PersonEnt;

import namedEntity.entities.Person;
import namedEntity.Theme;

import java.util.List;

public class Title extends Person {
    private String canonicalForm;
    private String profession;

    public Title(String name, List<String> category, int frequency, Theme theme, int id, String canonicalForm, String profession) {
        super(name, category, frequency, theme, id);
        this.canonicalForm = canonicalForm;
        this.profession = profession;
    }

    // Getter and setter methods for the above attributes
}