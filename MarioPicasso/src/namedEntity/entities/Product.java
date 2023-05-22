package namedEntity.entities;

import namedEntity.NamedEntity;

public class Product extends NamedEntity {
    private boolean isCommercial;
    private String producer;

    public Product(String name, String category, int frequency, boolean isCommercial, String producer) {
        super(name, category, frequency);
        this.isCommercial = isCommercial;
        this.producer = producer;
    }

    // Getter and setter methods for the above attributes
}
