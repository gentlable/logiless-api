package logiless.web.model.entity.primarykey;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * バラ商品マスタープライマリキーエンティティ
 * 
 * @author nsh14789
 *
 */
public class BaraItemPKEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	@Id
	private String tenpoCode;

	@Id
	private String setItemCode;
}
