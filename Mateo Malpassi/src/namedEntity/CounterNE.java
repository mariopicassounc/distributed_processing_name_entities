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
            return new OtherNE(word, categoryList, frecuency, "");
        
        }else if(categoryList.get(0) == "Organization" && categoryList.get(1) == "Industry"){

            MapNamedEntity.put("Organization", MapNamedEntity.get("Organization") + frecuency);
            return new Organization(word, categoryList, frecuency, null, 0, null);
        
        }else if(categoryList.get(0).equals("LastName") && categoryList.get(1).equals("International")){
            
            MapNamedEntity.put("LastName", MapNamedEntity.get("LastName") + frecuency);
            MapNamedEntity.put("Person", MapNamedEntity.get("Person") + frecuency);
            return new LastName(word, categoryList, frecuency, 0, null, null);
        
        }else if(categoryList.get(0).equals("LastName") && categoryList.get(1).equals("Politics")){
            
            MapNamedEntity.put("LastName", MapNamedEntity.get("LastName") + frecuency);
            MapNamedEntity.put("Person", MapNamedEntity.get("Person") + frecuency);
            return new LastName(word, categoryList, frecuency, 0, null, null);
        
        }else if(categoryList.get(0).equals("LastName") && categoryList.get(1).equals("Music")){
            
            MapNamedEntity.put("LastName", MapNamedEntity.get("LastName") + frecuency);
            MapNamedEntity.put("Person", MapNamedEntity.get("Person") + frecuency);
            return new LastName(word, categoryList, frecuency, 0, null, null);
        
        }else if(categoryList.get(0) == "LastName" && categoryList.get(1) == "Futbol"){
            
            MapNamedEntity.put("LastName", MapNamedEntity.get("LastName") + frecuency);
            MapNamedEntity.put("Person", MapNamedEntity.get("Person") + frecuency);
            return new LastName(word, categoryList, frecuency, 0, null, null);
        
        }else if(categoryList.get(0) == "LastName" && categoryList.get(1) == "Tenis"){
            
            MapNamedEntity.put("LastName", MapNamedEntity.get("LastName") + frecuency);
            MapNamedEntity.put("Person", MapNamedEntity.get("Person") + frecuency);
            return new LastName(word, categoryList, frecuency, 0, null, null);
        
        }else if(categoryList.get(0) == "Country" && categoryList.get(1) == "International"){
            
            MapNamedEntity.put("Country", MapNamedEntity.get("Country") + frecuency);
            MapNamedEntity.put("Place", MapNamedEntity.get("Place") + frecuency);
            return new Country(word, categoryList, frecuency, 0, null);
        
        }else if(categoryList.get(0) == "Product" && categoryList.get(1) == "Industry"){

            MapNamedEntity.put("Product", MapNamedEntity.get("Product") + frecuency);
            return new Organization(word, categoryList, frecuency, null, 0, null);
        
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
            String[] parts = entity.split(" ");
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
}
