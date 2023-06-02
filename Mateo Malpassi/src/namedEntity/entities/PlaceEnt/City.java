package namedEntity.entities.PlaceEnt;

import java.util.List;

import namedEntity.Theme;
import namedEntity.entities.Place;

public class City extends Place {
    private Country country;
    private boolean isCapital;
    private int population;

    public City(String name, List<String> category, int frequency, Theme theme, Country country, boolean isCapital, int population) {
        super(name, category, frequency, theme);
        this.country = country;
        this.isCapital = isCapital;
        this.population = population;
    }

    // Getter and setter methods for the above attributes
}
