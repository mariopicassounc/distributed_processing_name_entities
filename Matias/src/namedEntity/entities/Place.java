package namedEntity.entities;
import java.util.List;

import namedEntity.NamedEntity;
import namedEntity.Theme;

public class Place extends NamedEntity {
    public Place(String name, List<String> category, int frequency, Theme theme) {
        super(name, category, frequency, theme);
    }
}
