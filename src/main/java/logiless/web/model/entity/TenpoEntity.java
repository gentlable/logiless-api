package logiless.web.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

/**
 * 店舗マスターエンティティ
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@Entity
@Table(name = "API_M_TENPO")
public class TenpoEntity {

	@Id
	private String code;

	private String name;

	@Version
	private Long version;

}
