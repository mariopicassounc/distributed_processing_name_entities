package namedEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namedEntity.entities.OtherNE;
import namedEntity.entities.Product;
import namedEntity.entities.Organization;
import namedEntity.entities.PersonEnt.LastName;
import namedEntity.entities.PlaceEnt.Country;

import namedEntity.heuristic.Heuristic;

import namedEntity.themes.Futbol;
import namedEntity.themes.International;
import namedEntity.themes.Technology;
import namedEntity.themes.Tenis;

import scala.Tuple2;

public class FactoryNamedEntity {
    private Map<String, Integer> classFrequencyEntityMap = new HashMap<String, Integer>(){
		{
			put("LastName", 0);
            put("Person", 0);
            put("Country", 0);
            put("Place", 0);
            put("Organization", 0);
            put("Product", 0);
            put("OtherNE", 0);
            put("TotalNE", 0);
		}
	};
    private List<NamedEntity> listNamedEntities;

    public List <NamedEntity> createListNamedEntitys(Heuristic h, List<Tuple2<String, Integer>> output) {
        /*
         * Create a list of Named Entities from a List of Tuples word 
         * parameters: frequency and selected heuristic
         * Returns List of NE
         */

        this.listNamedEntities = new ArrayList<NamedEntity>();

        for (Tuple2<?, ?> tuple : output) {
            String word = (String) tuple._1();
            Integer frequency = (Integer) tuple._2();

            // If it is a named entity, add it to the list
            if (h.isEntity(word)) {
                List<String> category = h.getCategory(word);
                FactoryNamedEntity factoryNamedEntity = new FactoryNamedEntity();
                NamedEntity namedEntity = factoryNamedEntity.createNamedEntity(word, category, frequency);
                this.listNamedEntities.add(namedEntity);
            }
        }
        return listNamedEntities;
    }
    
    private NamedEntity createNamedEntity(String name, List<String> category, int frequency) {
        /*
         * Assumes that the name is a NamedEntity, checked this condition with the
         * heuristic rules.
         * Updates values in the classFrequencyEntityMap
         */

        // All the cases covered by the heuristic Map
        if (category == null){
            return new OtherNE(name, category, frequency, null, null);
        }
        else if (category.get(0).equals("LastName") && category.get(1).equals("International")) {
            
            classFrequencyEntityMap.put("LastName", classFrequencyEntityMap.get("LastName") + frequency);
            classFrequencyEntityMap.put("Person", classFrequencyEntityMap.get("Person") + frequency);

            Theme theme = new International();
            return new LastName(name, category, frequency, theme, 0, null, null);
        } 
        else if (category.get(0).equals("LastName") && category.get(1).equals("Futbol")){
            
            classFrequencyEntityMap.put("LastName", classFrequencyEntityMap.get("LastName") + frequency);
            classFrequencyEntityMap.put("Person", classFrequencyEntityMap.get("Person") + frequency);

            Theme theme = new Futbol();
            return new LastName(name, category, frequency, theme, 0, null, null);
        }
        else if (category.get(0).equals("LastName") && category.get(1).equals("Tenis")){

            classFrequencyEntityMap.put("LastName", classFrequencyEntityMap.get("LastName") + frequency);
            classFrequencyEntityMap.put("Person", classFrequencyEntityMap.get("Person") + frequency);

            Theme theme = new Tenis();
            return new LastName(name, category, frequency, theme, 0, null, null);
        }
        else if (category.get(0).equals("Country") && category.get(1).equals("International")){

            classFrequencyEntityMap.put("Country", classFrequencyEntityMap.get("Country") + frequency);
            classFrequencyEntityMap.put("Place", classFrequencyEntityMap.get("Place") + frequency);

            Theme theme = new International();
            return new Country(name, category, frequency, theme, 0, null);
        }
        else if (category.get(0).equals("Organization") && category.get(1).equals("Technology")){

            classFrequencyEntityMap.put("Organization", classFrequencyEntityMap.get("Organization") + frequency);

            Theme theme = new Technology();
            return new Organization(name, category, frequency, theme, null, 0, null);
        }
        else if (category.get(0).equals("Product") && category.get(1).equals("Technology")){

            classFrequencyEntityMap.put("Product", classFrequencyEntityMap.get("Product") + frequency);

            Theme theme = new Technology();
            return new Product (name, category, frequency, theme, false, null);
        }
        else {
            throw new RuntimeException("Invalid category of Named Entity");
        }
    }

    public void preetyPrint() {
        /*
         * Prints the classFrequencyEntityMap and the list of Named Entities with frequency
         */
        System.out.println("Class Frequency Entity Map:");
        for (Map.Entry<String, Integer> entry : classFrequencyEntityMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("List of Named Entities:");

        for (NamedEntity namedEntity : listNamedEntities) {
            System.out.println(namedEntity.getName() + " " + namedEntity.getFrequency());
        }
    }
}