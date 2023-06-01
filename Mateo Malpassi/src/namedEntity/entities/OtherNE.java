package namedEntity.entities;

import java.util.List;

import namedEntity.NamedEntity;

public class OtherNE extends NamedEntity {
    private String comments;

    public OtherNE(String name, List<String> categoryList, int frequency, String comments) {
        super(name, categoryList, frequency);
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}