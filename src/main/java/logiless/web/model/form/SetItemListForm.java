package logiless.web.model.form;

import java.util.List;

import logiless.web.model.dto.SetItem;
import lombok.Getter;
import lombok.Setter;

/**
 * セット商品一覧フォーム
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
public class SetItemListForm {

	private List<SetItem> setItemList;

}