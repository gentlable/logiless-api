package logiless.web.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import logiless.web.model.entity.primarykey.BaraItemPKEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "API_M_BARA_ITEM")
@IdClass(BaraItemPKEntity.class)
public class BaraItemEntity {

	@Id
	private String code;

	private String name;

	@Id
	private String tenpoCode;

	@Id
	private String setItemCode;

	private int quantity;

	private double price;

}
