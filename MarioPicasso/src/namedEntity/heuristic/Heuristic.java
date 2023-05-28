package namedEntity.heuristic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Heuristic {

	private static Map<String, List<String>> categoryMap = new HashMap<String, List<String>>(){
		{
			put("Microsoft", Arrays.asList("Organization", "Technology"));
			put("Apple", Arrays.asList("Organization", "Technology"));
			put("Google", Arrays.asList("Organization", "Technology"));
			put("IBM", Arrays.asList("Organization", "Technology"));
			put("Musk", Arrays.asList("LastName", "International"));
			put("Biden", Arrays.asList("LastName", "International"));
			put("Trump", Arrays.asList("LastName", "International"));
			put("Messi", Arrays.asList( "LastName", "Futbol"));
			put("Federer", Arrays.asList("LastName", "Tenis"));
			put("Nadal", Arrays.asList("LastName", "Tenis"));
			put("USA", Arrays.asList("Country", "International"));
			put("Russia", Arrays.asList("Country", "International"));
			put("ChatGPT", Arrays.asList("Product", "Technology"));
		}
	};
	
	
	public List <String> getCategory(String entity){
		return categoryMap.get(entity);
	}
	
	
	public abstract boolean isEntity(String word);
		
}
