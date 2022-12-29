package logiless.web.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaraItem {
	
	private String code;
	
	private String name;
	
	private String tenpoCode;
	
	private String setItemCode;
	
	private int amount;
	
	private int price;
}
