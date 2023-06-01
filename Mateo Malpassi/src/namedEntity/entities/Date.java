package namedEntity.entities;

import java.util.List;

import namedEntity.NamedEntity;

public class Date extends NamedEntity {
    private boolean isExact;
    private String canonicalForm;

    public Date(String name, List<String> category, int frequency, boolean isExact, String canonicalForm) {
        super(name, category, frequency);
        this.isExact = isExact;
        this.canonicalForm = canonicalForm;
    }

    // Getter and setter methods for the above attributes
}