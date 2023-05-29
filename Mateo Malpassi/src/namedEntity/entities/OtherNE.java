package namedEntity.entities;

import namedEntity.NamedEntity;

public class OtherNE extends NamedEntity {
    private String comments;

    public OtherNE(String name, String category, int frequency, String comments) {
        super(name, category, frequency);
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}