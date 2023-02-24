package logiless.web.model.entity;

import javax.persistence.Column;
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
	@Column(name = "sgyosya_cd")
	private String sgyosyaCd;

	@Column(name = "sgyosya_nm")
	private String sgyosyaNm;

	@Column(name = "password_cd")
	private String passwordCd;

	@Column(name = "siyou_teisi_fl")
	private String siyouTeisiFl;

}
