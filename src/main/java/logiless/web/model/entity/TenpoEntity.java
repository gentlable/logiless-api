package logiless.web.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "api_m_tenpo")
public class TenpoEntity {
	
	@Id
	@Column
	private String code;
	
	@Column
	private String name;

}
