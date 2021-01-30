package tacos.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {

	private Long id;
	private Date createdAt;
	
//	@NotNull(message = "Name is empty")
	@Size(min = 5, message = "Name must be at least 5 characters long")
	private String name;
	
//	@NotNull(message = "Ingredients is empty")
	@Size(min= 1, message = "Tou must choose at least 1 ingredient")
	private List<Ingredient> ingredients = new ArrayList<>();
	
}
