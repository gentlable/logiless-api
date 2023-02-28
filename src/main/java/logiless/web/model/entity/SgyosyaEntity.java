package logiless.web.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 作業者マスターエンティティ
 * 
 * @author nsh14789
 *
 */
@Entity
@Table(name = "M_SGYOSYA")
@Getter
@Setter
public class SgyosyaEntity {

	@Id
	private String sgyosyaCd;

	private String sgyosyaNm;

	private String passwordCd;

	private String siyouTeisiFl;

}
