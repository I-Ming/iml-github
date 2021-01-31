package tacos.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {

	private long id;
	
	private Date createdAt;

	@Size(min = 5, message = "Taco name must have 5 characters at least.")
	private String name;
	
	@Size(min = 1, message = "Ingredients must have one at least.")
	private List<Ingredient> ingredients = new ArrayList<>();
	
}
