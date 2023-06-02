package namedEntity.entities;

import java.util.List;

import namedEntity.NamedEntity;
import namedEntity.Theme;

public class Date extends NamedEntity {
    private boolean isExact;
    private String canonicalForm;

    public Date(String name, List<String> category, int frequency, Theme theme, boolean isExact, String canonicalForm) {
        super(name, category, frequency, theme);
        this.isExact = isExact;
        this.canonicalForm = canonicalForm;
    }

    // Getter and setter methods for the above attributes
}