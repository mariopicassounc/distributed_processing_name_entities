package namedEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namedEntity.entities.Organization;
import namedEntity.entities.OtherNE;
import namedEntity.entities.PersonEnt.LastName;
import namedEntity.entities.PlaceEnt.Country;

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
    
    // Según la categoría de la entidad nombrada, se actualiza el valor de frecuencia de la entidad nombrada en el mapa
    private NamedEntity CreateNE(String word, List<String> categoryList, Integer frecuency) throws Exception {
        
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
}
