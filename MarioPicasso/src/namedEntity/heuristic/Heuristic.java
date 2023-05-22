package namedEntity.heuristic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Heuristic {

	private static Map<String, List<String>> categoryMap = new HashMap<String, List<String>>(){
		{
			put("Microsoft", Arrays.asList("Organization", "OtherNE"));
			put("Apple", Arrays.asList("Organization", "OtherNE"));
			put("Google", Arrays.asList("Organization", "OtherNE"));
			put("Musk", Arrays.asList("LastName", "International"));
			put("Biden", Arrays.asList("LastName", "International"));
			put("Trump", Arrays.asList("LastName", "International"));
			put("Messi", Arrays.asList( "LastName", "Futbol"));
			put("Federer", Arrays.asList("LastName", "Tenis"));
			put("USA", Arrays.asList("Country", "International"));
			put("Russia", Arrays.asList("Country", "International"));
		}
	};
	
	
	public List <String> getCategory(String entity){
		return categoryMap.get(entity);
	}
	
	
	public abstract boolean isEntity(String word);
		
}
