package namedEntity.entities.PlaceEnt;

import namedEntity.entities.Place;

public class Address extends Place {
    private City city;
    private String canonicalForm;

    public Address(String name, String category, int frequency, City city, String canonicalForm) {
        super(name, category, frequency);
        this.city = city;
        this.canonicalForm = canonicalForm;
    }

    // Getter and setter methods for the above attributes
}