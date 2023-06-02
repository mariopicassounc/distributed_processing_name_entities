package namedEntity.entities;

import java.util.List;

import namedEntity.NamedEntity;
import namedEntity.Theme;

public class Product extends NamedEntity {
    private boolean isCommercial;
    private String producer;

    public Product(String name, List<String> category, int frequency,Theme theme, boolean isCommercial, String producer) {
        super(name, category, frequency, theme);
        this.isCommercial = isCommercial;
        this.producer = producer;
    }

    // Getter and setter methods for the above attributes
}
