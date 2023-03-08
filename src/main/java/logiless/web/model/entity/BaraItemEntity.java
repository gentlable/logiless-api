package logiless.web.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Version;

import logiless.web.model.entity.primarykey.BaraItemPKEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * ロジレスバラ商品マスターエンティティ
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@Entity
@Table(name = "ロジレスバラ商品マスター")
@IdClass(BaraItemPKEntity.class)
public class BaraItemEntity {

	@Id
	@Column(name = "バラ商品コード")
	private String baraItemCd;

	@Column(name = "バラ商品名")
	private String baraItemNm;

	@Id
	@Column(name = "店舗コード")
	private String tenpoCd;

	@Id
	@Column(name = "セット商品コード")
	private String setItemCd;

	@Column(name = "数量")
	private int quantity;

	@Column(name = "単価")
	private double price;

	@Version
	@Column(name = "更新度数")
	private Long version;

}
