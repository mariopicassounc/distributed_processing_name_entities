package namedEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namedEntity.entities.Organization;
import namedEntity.entities.OtherNE;
import namedEntity.entities.PersonEnt.LastName;
import namedEntity.entities.PlaceEnt.Country;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.themes.Futbol;
import namedEntity.themes.Industry;
import namedEntity.themes.International;
import namedEntity.themes.Music;
import namedEntity.themes.Politics;
import namedEntity.themes.Tenis;

public class CounterNE {
    
    // Lista donde se guardarán las entidades nombradas
    private List<NamedEntity> ListNamedEntity;
     
    // Mapa <categoría,frecuencia> según cada entidad nombrada
    private Map<String,Integer> MapNamedEntity = new HashMap<String,Integer>();{
        {
            MapNamedEntity.put("Organization", 0);
            MapNamedEntity.put("OtherNE", 0);
            MapNamedEntity.put("Person", 0);
            MapNamedEntity.put("LastName", 0);
            MapNamedEntity.put("Place", 0);
            MapNamedEntity.put("Country", 0);
            MapNamedEntity.put("Product", 0);
        }
    }
    
    private NamedEntity createNE(String word, List<String> categoryList, Integer frecuency) throws Exception {
    
        // Según la categoría de la entidad nombrada, se crea y se actualiza el valor de frecuencia de la entidad nombrada en el mapa
        if(categoryList == null){

            MapNamedEntity.put("OtherNE", MapNamedEntity.get("OtherNE") + frecuency);
            return new OtherNE(word, categoryList, frecuency, null, "");
        
        }else if(categoryList.get(0) == "Organization" && categoryList.get(1) == "Industry"){

            MapNamedEntity.put("Organization", MapNamedEntity.get("Organization") + frecuency);
            Theme theme = new Industry();
            return new Organization(word, categoryList, frecuency, theme, null, 0, null);
        
        }else if(categoryList.get(0).equals("LastName") && categoryList.get(1).equals("International")){
            
            MapNamedEntity.put("LastName", MapNamedEntity.get("LastName") + frecuency);
            MapNamedEntity.put("Person", MapNamedEntity.get("Person") + frecuency);
            Theme theme = new International();
            return new LastName(word, categoryList, frecuency, theme, 0, null, null);
        
        }else if(categoryList.get(0).equals("LastName") && categoryList.get(1).equals("Politics")){
            
            MapNamedEntity.put("LastName", MapNamedEntity.get("LastName") + frecuency);
            MapNamedEntity.put("Person", MapNamedEntity.get("Person") + frecuency);
            Theme theme = new Politics();
            return new LastName(word, categoryList, frecuency, theme, 0, null, null);
        
        }else if(categoryList.get(0).equals("LastName") && categoryList.get(1).equals("Music")){
            
            MapNamedEntity.put("LastName", MapNamedEntity.get("LastName") + frecuency);
            MapNamedEntity.put("Person", MapNamedEntity.get("Person") + frecuency);
            Theme theme = new Music();
            return new LastName(word, categoryList, frecuency, theme, 0, null, null);
        
        }else if(categoryList.get(0) == "LastName" && categoryList.get(1) == "Futbol"){
            
            MapNamedEntity.put("LastName", MapNamedEntity.get("LastName") + frecuency);
            MapNamedEntity.put("Person", MapNamedEntity.get("Person") + frecuency);
            Theme theme = new Futbol();
            return new LastName(word, categoryList, frecuency, theme, 0, null, null);
        
        }else if(categoryList.get(0) == "LastName" && categoryList.get(1) == "Tenis"){
            
            MapNamedEntity.put("LastName", MapNamedEntity.get("LastName") + frecuency);
            MapNamedEntity.put("Person", MapNamedEntity.get("Person") + frecuency);
            Theme theme = new Tenis();
            return new LastName(word, categoryList, frecuency, theme, 0, null, null);
        
        }else if(categoryList.get(0) == "Country" && categoryList.get(1) == "International"){
            
            MapNamedEntity.put("Country", MapNamedEntity.get("Country") + frecuency);
            MapNamedEntity.put("Place", MapNamedEntity.get("Place") + frecuency);
            Theme theme = new International();
            return new Country(word, categoryList, frecuency, theme, 0, null);
        
        }else if(categoryList.get(0) == "Product" && categoryList.get(1) == "Industry"){

            MapNamedEntity.put("Product", MapNamedEntity.get("Product") + frecuency);
            Theme theme = new Industry();
            return new Organization(word, categoryList, frecuency, theme, null, 0, null);
        
        }else{
            throw new Exception("Not a valid category");
        }
    }

    public List<NamedEntity> getListNamedEntity(List<String> entityList, Heuristic heuristic) throws Exception {

        /*
        * Añado a ListNamedEntity las entidades nombradas que cumplen con la heurística
        * A su vez, utilizo CreateNE para crear la entidad nombrada y actualizar el valor de frecuencia en el mapa
        */

        this.ListNamedEntity = new ArrayList<NamedEntity>();
        
        for (String entity : entityList) {
            String[] parts = entity.split(": ");
            String word = parts[0];
            Integer frecuency = Integer.parseInt(parts[1]);
            if (heuristic.isEntity(word)) {
                List<String> categoryList = heuristic.getCategory(word);
                NamedEntity namedEntity = createNE(word, categoryList, frecuency);
                ListNamedEntity.add(namedEntity);
            }
        }
        return ListNamedEntity;
    }

    public void prettyPrint(){
        System.out.println("*****Category Map Frecuency*****");
        for (Map.Entry<String, Integer> entry : MapNamedEntity.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("*****Named Entity List*****");
        for (NamedEntity namedEntity : ListNamedEntity) {
            namedEntity.prettyPrint();
        }
    }

    public static void main(String[] args) throws Exception{
        //Test CounterNE
        List<String> entityList = new ArrayList<String>();
        entityList.add("Musk: 50");
        entityList.add("Iphone: 50");
        entityList.add("Microsoft: 50");
        entityList.add("USA: 50");
        entityList.add("AFA: 50");

        Heuristic heuristic = new QuickHeuristic();
        CounterNE counterNE = new CounterNE();
        List <NamedEntity> neList = counterNE.getListNamedEntity(entityList, heuristic);
        counterNE.prettyPrint();
    }
}