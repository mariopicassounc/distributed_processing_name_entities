package namedEntity;

import java.util.List;

import namedEntity.entities.OtherNE;
import namedEntity.entities.PersonEnt.LastName;
import namedEntity.themes.International;

public class FactoryNamedEntity {
    public static NamedEntity createNamedEntity(String name, List<String> category, int frequency) {
        /*
         * Assumes that the name is a NamedEntity, checked this condition with the
         * heuristic rules
         */

        if (category.get(0).equals("LastName") && category.get(1).equals("International")) {
            Theme theme = new International();
            return new LastName(name, category, frequency, theme, 0, null, null);
        } else {
            return new OtherNE(name, category, frequency, null, null);
        }
    }

}