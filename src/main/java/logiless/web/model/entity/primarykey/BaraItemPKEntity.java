package logiless.web.model.entity.primarykey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ロジレスバラ商品マスタープライマリキーエンティティ
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaraItemPKEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String baraItemCd;

	private String tenpoCd;

	private String setItemCd;
}
