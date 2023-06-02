package namedEntity.entities;

import namedEntity.NamedEntity;
import namedEntity.Theme;

import java.util.List;

public class OtherNE extends NamedEntity {
    private String comments;

    public OtherNE(String name, List<String> category, int frequency, Theme theme, String comments) {
        super(name, category, frequency, theme);
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}