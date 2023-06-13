package namedEntity.entities.PlaceEnt;

import namedEntity.entities.Place;
import namedEntity.Theme;

import java.util.List;

public class Other extends Place {
    private String comments;

    public Other(String name, List<String> category, int frequency, Theme theme, String comments) {
        super(name, category, frequency, theme);
        this.comments = comments;
    }

    // Getter and setter methods for the above attributes
}