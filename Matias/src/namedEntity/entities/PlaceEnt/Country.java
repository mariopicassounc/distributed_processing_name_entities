package namedEntity.entities.PlaceEnt;

import namedEntity.entities.Place;
import namedEntity.Theme;

import java.util.List;

public class Country extends Place {
    private int population;
    private String language;

    public Country(String name, List<String> category, int frequency, Theme theme, int population, String language) {
        super(name, category, frequency, theme);
        this.population = population;
        this.language = language;
    }

    // Getter and setter methods for the above attributes
}
