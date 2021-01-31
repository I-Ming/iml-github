package tacos.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Order {

	private long id;
	
	private Date placedAt;
	
	@NotBlank(message = "")
	private String deliveryName;
	
	private String deliverySrteet;
	
	private String deliveryCity;
	
	private String deliveryState;
	
	private String deliveryZip;
	
	private String ccNumber;
	
	private String ccExpiration;
	
	private String ccCVV;
	
	private List<Taco> tacos = new ArrayList<>();
	
	public void addDesign(Taco design) {
		tacos.add(design);
	}
	
}
