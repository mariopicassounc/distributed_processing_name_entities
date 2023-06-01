package namedEntity.entities;
import java.util.List;

import namedEntity.NamedEntity;

public class Place extends NamedEntity {
    public Place(String name, List<String> categoryList, int frequency) {
        super(name, categoryList, frequency);
    }
}