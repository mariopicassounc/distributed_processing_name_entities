package namedEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
}
