package namedEntity.entities;

import java.util.List;

import namedEntity.NamedEntity;
import namedEntity.Theme;

public class Organization extends NamedEntity {
    private String canonicalForm;
    private int numberOfMembers;
    private String organizationType;

    public Organization(String name, List<String> categoryList, int frequency, Theme theme, String canonicalForm, int numberOfMembers, String organizationType) {
        super(name, categoryList, frequency, theme);
        this.canonicalForm = canonicalForm;
        this.numberOfMembers = numberOfMembers;
        this.organizationType = organizationType;
    }

    // Getter and setter methods for the above attributes
}