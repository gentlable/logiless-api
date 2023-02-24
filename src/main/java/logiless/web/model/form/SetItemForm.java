package logiless.web.model.form;

import java.util.List;

import javax.validation.Valid;

import logiless.web.model.dto.BaraItem;
import logiless.web.model.dto.SetItem;
import lombok.Getter;
import lombok.Setter;

/**
 * セット商品登録フォーム
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
public class SetItemForm {

	private String tenpoName;

	@Valid
	private SetItem setItem;

	@Valid
	private List<BaraItem> baraItemList;

	private boolean editFlg;
}
