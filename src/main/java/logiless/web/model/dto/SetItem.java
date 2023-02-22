package logiless.web.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import logiless.web.model.dto.SetItem.InsertData;
import logiless.web.model.dto.SetItem.UpdateData;
import logiless.web.validator.SetItemDuplicate;
import logiless.web.validator.SetItemExist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SetItemDuplicate(groups = { InsertData.class }, code = "code", tenpoCode = "tenpoCode")
@SetItemExist(groups = { UpdateData.class }, code = "code", tenpoCode = "tenpoCode")
public class SetItem {

	public static interface InsertData {
	}

	public static interface UpdateData {
	}

	@NotEmpty(message = "セット商品コードを入力してください")
	@Pattern(regexp = "^[0-9]{10}$", message = "セット商品コードは半角数字10桁で入力してください。")
	private String code;

	private String name;

	@NotEmpty(message = "店舗を選択してください")
	@Pattern(regexp = "^[0-9]{4}$", message = "店舗コードは半角数字4桁で入力してください。")
	private String tenpoCode;

	/**
	 * セット商品マスタ選択用チェックボックス
	 */
	private boolean check;
}
