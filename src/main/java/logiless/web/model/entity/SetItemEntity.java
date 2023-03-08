package logiless.web.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Version;

import logiless.web.model.entity.primarykey.SetItemPKEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * ロジレスセット商品マスターエンティティ
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@Entity
@IdClass(SetItemPKEntity.class)
@Table(name = "ロジレスセット商品マスター")
public class SetItemEntity {

	@Id
	@Column(name = "セット商品コード")
	private String setItemCd;

	@Column(name = "セット商品名")
	private String setItemNm;

	@Id
	@Column(name = "店舗コード")
	private String tenpoCd;

	@Version
	@Column(name = "更新度数")
	private Long version;

}
