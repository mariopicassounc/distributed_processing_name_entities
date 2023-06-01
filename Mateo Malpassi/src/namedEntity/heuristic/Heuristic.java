package namedEntity.heuristic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Heuristic {

	private static Map<String, List<String>> categoryMap = new HashMap<String, List<String>>(){
		{
			put("Microsoft", Arrays.asList("Organization", "Industry"));
			put("Apple", Arrays.asList("Organization", "Industry"));
			put("Google", Arrays.asList("Organization", "Industry"));
			put("Musk", Arrays.asList("LastName", "International"));
			put("Biden", Arrays.asList("LastName", "Politics"));
			put("Trump", Arrays.asList("LastName", "Politics"));
			put("Messi", Arrays.asList( "LastName", "Futbol"));
			put("Federer", Arrays.asList("LastName", "Tenis"));
			put("Phil Collins", Arrays.asList("LastName", "Music"));
			put("USA", Arrays.asList("Country", "International"));
			put("Russia", Arrays.asList("Country", "International"));
			put("Iphone", Arrays.asList("Product", "Industry"));
		}
	};
	
	
	public List <String> getCategory(String entity){
		return categoryMap.get(entity);
	}
	
	
	public abstract boolean isEntity(String word);
		
}
