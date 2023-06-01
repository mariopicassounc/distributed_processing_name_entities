package namedEntity.entities.PlaceEnt;

import namedEntity.entities.Place;

public class Other extends Place {
    private String comments;

    public Other(String name, String category, int frequency, String comments) {
        super(name, category, frequency);
        this.comments = comments;
    }

    // Getter and setter methods for the above attributes
}