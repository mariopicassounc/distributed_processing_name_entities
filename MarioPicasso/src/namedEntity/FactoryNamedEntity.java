package namedEntity;

import java.util.List;

import namedEntity.entities.OtherNE;
import namedEntity.entities.Product;
import namedEntity.entities.Organization;
import namedEntity.entities.PersonEnt.LastName;
import namedEntity.entities.PlaceEnt.Country;


import namedEntity.themes.Futbol;
import namedEntity.themes.International;
import namedEntity.themes.Technology;
import namedEntity.themes.Tenis;

public class FactoryNamedEntity {
    public static NamedEntity createNamedEntity(String name, List<String> category, int frequency) {
        /*
         * Assumes that the name is a NamedEntity, checked this condition with the
         * heuristic rules
         */

        // All the cases covered by the heuristic Map

        if (category.get(0).equals("LastName") && category.get(1).equals("International")) {
            Theme theme = new International();
            return new LastName(name, category, frequency, theme, 0, null, null);
        } 
        else if (category.get(0).equals("LastName") && category.get(1).equals("Futbol")){
            Theme theme = new Futbol();
            return new LastName(name, category, frequency, theme, 0, null, null);
        }
        else if (category.get(0).equals("LastName") && category.get(1).equals("Tenis")){
            Theme theme = new Tenis();
            return new LastName(name, category, frequency, theme, 0, null, null);
        }
        else if (category.get(0).equals("Country") && category.get(1).equals("International")){
            Theme theme = new International();
            return new Country(name, category, frequency, theme, 0, null);
        }
        else if (category.get(0).equals("Organization") && category.get(1).equals("Technology")){
            Theme theme = new Technology();
            return new Organization(name, category, frequency, theme, null, 0, null);
        }
        else if (category.get(0).equals("Product") && category.get(1).equals("Technology")){
            Theme theme = new Technology();
            return new Product (name, category, frequency, theme, false, null);
        }
        else {
            return new OtherNE(name, category, frequency, null, null);
        }
    }

}