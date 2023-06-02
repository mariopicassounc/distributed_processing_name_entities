package namedEntity;

import java.util.List;

/*Esta clase modela la nocion de entidad nombrada*/

public class NamedEntity {
	protected String name;
	protected List<String> category; 
	protected int frequency;
	protected Theme theme;
	
	public NamedEntity(String name, List<String> categoryList, int frequency, Theme theme) {
		this.name = name;
		this.category = categoryList;
		this.frequency = frequency;
		this.theme = theme;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCategory() {
		return this.category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public int getFrequency() {
		return this.frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void incFrequency() {
		this.frequency++;
	}

	@Override
	public String toString() {
		return "ObjectNamedEntity [name=" + name + ", frequency=" + frequency + "]";
	}
	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency());
	}
	
	
}



