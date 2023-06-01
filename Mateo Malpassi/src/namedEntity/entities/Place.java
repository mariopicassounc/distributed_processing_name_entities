package namedEntity.entities;
import java.util.List;

import namedEntity.NamedEntity;
import namedEntity.Theme;

public class Place extends NamedEntity {
    public Place(String name, List<String> categoryList, int frequency, Theme theme) {
        super(name, categoryList, frequency, theme);
    }
}