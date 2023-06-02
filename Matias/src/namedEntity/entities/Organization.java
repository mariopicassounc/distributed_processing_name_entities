package namedEntity.entities;

import namedEntity.NamedEntity;
import namedEntity.Theme;

import java.util.List;

public class Organization extends NamedEntity {
    private String canonicalForm;
    private int numberOfMembers;
    private String organizationType;

    public Organization(String name, List<String> category, int frequency, Theme theme, String canonicalForm, int numberOfMembers, String organizationType) {
        super(name, category, frequency, theme);
        this.canonicalForm = canonicalForm;
        this.numberOfMembers = numberOfMembers;
        this.organizationType = organizationType;
    }

    // Getter and setter methods for the above attributes
}
