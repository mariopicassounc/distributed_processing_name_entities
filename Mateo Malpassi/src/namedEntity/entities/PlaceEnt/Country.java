package namedEntity.entities.PlaceEnt;

import java.util.List;

import namedEntity.entities.Place;

public class Country extends Place {
    private int population;
    private String language;

    public Country(String name, List<String> categoryList, int frequency, int population, String language) {
        super(name, categoryList, frequency);
        this.population = population;
        this.language = language;
    }

    // Getter and setter methods for the above attributes
}