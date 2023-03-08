package logiless.web.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

/**
 * ロジレス店舗マスターエンティティ
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@Entity
@Table(name = "ロジレス店舗マスター")
public class TenpoEntity {

	@Id
	@Column(name = "店舗コード")
	private String tenpoCd;

	@Column(name = "店舗名")
	private String tenpoNm;

	@Column(name = "プラットフォーム")
	private String platform;

	@Version
	@Column(name = "更新度数")
	private Long version;

}
