package namedEntity.entities;

import java.util.List;

import namedEntity.NamedEntity;

public class Organization extends NamedEntity {
    private String canonicalForm;
    private int numberOfMembers;
    private String organizationType;

    public Organization(String name, List<String> categoryList, int frequency, String canonicalForm, int numberOfMembers, String organizationType) {
        super(name, categoryList, frequency);
        this.canonicalForm = canonicalForm;
        this.numberOfMembers = numberOfMembers;
        this.organizationType = organizationType;
    }

    // Getter and setter methods for the above attributes
}