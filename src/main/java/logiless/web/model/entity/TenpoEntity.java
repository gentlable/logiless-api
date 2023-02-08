package logiless.web.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "API_M_TENPO")
public class TenpoEntity {

	@Id
	private String code;

	private String name;

}
