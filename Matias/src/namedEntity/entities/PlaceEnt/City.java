package namedEntity.entities.PlaceEnt;

import namedEntity.entities.Place;

public class City extends Place {
    private Country country;
    private boolean isCapital;
    private int population;

    public City(String name, String category, int frequency, Country country, boolean isCapital, int population) {
        super(name, category, frequency);
        this.country = country;
        this.isCapital = isCapital;
        this.population = population;
    }

    // Getter and setter methods for the above attributes
}
