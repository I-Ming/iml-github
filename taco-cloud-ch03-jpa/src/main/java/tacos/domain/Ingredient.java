package tacos.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Ingredient {

	private String id;
	
	private String name;
	
	private Type type;
	
	public enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
	
}
