package logiless.web.model.entity.primarykey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ロジレスセット商品マスタープライマリキーエンティティ
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetItemPKEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String setItemCd;

	private String tenpoCd;

}
