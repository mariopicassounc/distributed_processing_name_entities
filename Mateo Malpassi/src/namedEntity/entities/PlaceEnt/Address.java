package namedEntity.entities.PlaceEnt;

import java.util.List;

import namedEntity.Theme;
import namedEntity.entities.Place;

public class Address extends Place {
    private City city;
    private String canonicalForm;

    public Address(String name, List<String> category, int frequency, Theme theme, City city, String canonicalForm) {
        super(name, category, frequency, theme);
        this.city = city;
        this.canonicalForm = canonicalForm;
    }

    // Getter and setter methods for the above attributes
}