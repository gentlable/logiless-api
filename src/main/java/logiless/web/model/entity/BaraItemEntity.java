package logiless.web.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "api_m_bara_item")
public class BaraItemEntity {
	
	@Id
	private String code;
	
	private String name;
	
	private String tenpoCode;
	
	private String setItemCode;
	
	private int amount;
	
	private int price;

}
