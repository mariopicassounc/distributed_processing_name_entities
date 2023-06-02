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
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.themes.Futbol;
import namedEntity.themes.International;
import namedEntity.themes.Technology;
import namedEntity.themes.Tenis;

import scala.Tuple2;

public class FactoryNamedEntity {
    private Map<String, Integer> classFrequencyEntityMap = new HashMap<String, Integer>() {
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

    public List<NamedEntity> createListNamedEntities(Heuristic heuristic, List<Tuple2<String, Integer>> output) {
        this.listNamedEntities = new ArrayList<>();

        for (Tuple2<String, Integer> tuple : output) {
            String word = tuple._1();
            Integer frequency = tuple._2();

            if (heuristic.isEntity(word)) {
                List<String> category = heuristic.getCategory(word);
                NamedEntity namedEntity = createNamedEntity(word, category, frequency);
                this.listNamedEntities.add(namedEntity);
            }
        }

        return listNamedEntities;
    }

    private NamedEntity createNamedEntity(String name, List<String> category, int frequency) {
        if (category == null) {
            return new OtherNE(name, category, frequency, null, null);
        } else if (category.get(0).equals("LastName") && category.get(1).equals("International")) {
            classFrequencyEntityMap.put("LastName", classFrequencyEntityMap.get("LastName") + frequency);
            classFrequencyEntityMap.put("Person", classFrequencyEntityMap.get("Person") + frequency);
            Theme theme = new International();
            return new LastName(name, category, frequency, theme, 0, null, null);
        } else if (category.get(0).equals("LastName") && category.get(1).equals("Futbol")) {
            classFrequencyEntityMap.put("LastName", classFrequencyEntityMap.get("LastName") + frequency);
            classFrequencyEntityMap.put("Person", classFrequencyEntityMap.get("Person") + frequency);
            Theme theme = new Futbol();
            return new LastName(name, category, frequency, theme, 0, null, null);
        } else if (category.get(0).equals("LastName") && category.get(1).equals("Tenis")) {
            classFrequencyEntityMap.put("LastName", classFrequencyEntityMap.get("LastName") + frequency);
            classFrequencyEntityMap.put("Person", classFrequencyEntityMap.get("Person") + frequency);
            Theme theme = new Tenis();
            return new LastName(name, category, frequency, theme, 0, null, null);
        } else if (category.get(0).equals("Country") && category.get(1).equals("International")) {
            classFrequencyEntityMap.put("Country", classFrequencyEntityMap.get("Country") + frequency);
            classFrequencyEntityMap.put("Place", classFrequencyEntityMap.get("Place") + frequency);
            Theme theme = new International();
            return new Country(name, category, frequency, theme, 0, null);
        } else if (category.get(0).equals("Organization") && category.get(1).equals("Technology")) {
            classFrequencyEntityMap.put("Organization", classFrequencyEntityMap.get("Organization") + frequency);
            Theme theme = new Technology();
            return new Organization(name, category, frequency, theme, null, 0, null);
        } else if (category.get(0).equals("Product") && category.get(1).equals("Technology")) {
            classFrequencyEntityMap.put("Product", classFrequencyEntityMap.get("Product") + frequency);
            Theme theme = new Technology();
            return new Product(name, category, frequency, theme, false, null);
        } else {
            throw new RuntimeException("Invalid category of Named Entity");
        }
    }

    public void prettyPrint() {
        System.out.println("\n\n\n****** Class Frequency Entity Map ********\n");
        for (Map.Entry<String, Integer> entry : this.classFrequencyEntityMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("\n\n\n********* List of Named Entities *********\n");

        for (NamedEntity namedEntity : listNamedEntities) {
            System.out.println(namedEntity.getName() + " " + namedEntity.getFrequency());
        }
    }

    public static void main(String[] args) {
        Heuristic heuristic = new QuickHeuristic();
        List<Tuple2<String, Integer>> output = new ArrayList<>();
        output.add(new Tuple2<>("Messi", 120));

        FactoryNamedEntity factoryNamedEntity = new FactoryNamedEntity();
        List<NamedEntity> namedEntities = factoryNamedEntity.createListNamedEntities(heuristic, output);
        factoryNamedEntity.prettyPrint();
    }
}
