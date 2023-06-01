package namedEntity;

import java.util.ArrayList;
import java.util.List;

import namedEntity.heuristic.Heuristic;
import scala.Tuple2;

public class ListNamedEntitys {
    private List<NamedEntity> namedEntities;

    public ListNamedEntitys(Heuristic h, List<Tuple2<String, Integer>> output) {
        /*
         * Create a list of Named Entities from a List of Tuples word: frecuency and a
         * selected hueristic
         */

        this.namedEntities = new ArrayList<NamedEntity>();

        for (Tuple2<?, ?> tuple : output) {
            String word = (String) tuple._1();
            Integer frequency = (Integer) tuple._2();

            // If it is a named entity, add it to the list
            if (h.isEntity(word)) {
                List<String> category = h.getCategory(word);


            }
        }
    }


    public void prettyPrint() {
        System.out.println("\n\n\n************* Named Entities *************");
        for (NamedEntity ne : this.namedEntities) {
            ne.prettyPrint();
        }
    }

}