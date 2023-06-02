package namedEntity.entities;

import namedEntity.NamedEntity;
import namedEntity.Theme;

import java.util.List;

public class Event extends NamedEntity {
    private String canonicalForm;
    private Date date;
    private String recurrence;

    public Event(String name, List<String> category, int frequency, Theme theme, String canonicalForm, Date date, String recurrence) {
        super(name, category, frequency, theme);
        this.canonicalForm = canonicalForm;
        this.date = date;
        this.recurrence = recurrence;
    }

    // Getter and setter methods for the above attributes
}
