package namedEntity.entities;

import java.util.List;

import namedEntity.NamedEntity;

public class Event extends NamedEntity {
    private String canonicalForm;
    private Date date;
    private String recurrence;

    public Event(String name, List<String> category, int frequency, String canonicalForm, Date date, String recurrence) {
        super(name, category, frequency);
        this.canonicalForm = canonicalForm;
        this.date = date;
        this.recurrence = recurrence;
    }

    // Getter and setter methods for the above attributes
}
