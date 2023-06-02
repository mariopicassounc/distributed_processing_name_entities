package namedEntity.entities;

import java.util.List;

import namedEntity.NamedEntity;
import namedEntity.Theme;

public class OtherNE extends NamedEntity {
    private String comments;

    public OtherNE(String name, List<String> categoryList, int frequency, Theme theme, String comments) {
        super(name, categoryList, frequency, theme);
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}