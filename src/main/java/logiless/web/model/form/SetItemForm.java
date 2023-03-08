package logiless.web.model.form;

import java.util.List;

import javax.validation.Valid;

import logiless.web.model.dto.BaraItem;
import logiless.web.model.dto.SetItem;
import logiless.web.validator.BaraItemDuplicate;
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

	private String tenpoNm;

	@Valid
	private SetItem setItem;

	@Valid
	@BaraItemDuplicate
	private List<BaraItem> baraItemList;

	private boolean editFlg;
}
