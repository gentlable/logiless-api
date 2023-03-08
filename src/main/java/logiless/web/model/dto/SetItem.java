package logiless.web.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import logiless.web.model.dto.SetItem.InsertData;
import logiless.web.model.dto.SetItem.UpdateData;
import logiless.web.validator.SetItemDuplicate;
import logiless.web.validator.SetItemExist;
import lombok.Getter;
import lombok.Setter;

/**
 * セット商品DTO
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@SetItemDuplicate(groups = { InsertData.class }, setItemCd = "setItemCd", tenpoCd = "tenpoCd")
@SetItemExist(groups = { UpdateData.class }, setItemCd = "setItemCd", tenpoCd = "tenpoCd")
public class SetItem {

	public static interface InsertData {
	}

	public static interface UpdateData {
	}

	@NotEmpty(message = "セット商品コードを入力してください")
	@Pattern(regexp = "^[0-9]{10}$", message = "セット商品コードは半角数字10桁で入力してください。")
	private String setItemCd;

	@Size(max = 100, message = "セット商品名は全角100桁以内で入力してください")
	@NotNull(message = "セット商品名を入力してください")
	private String setItemNm;

	@NotEmpty(message = "店舗を選択してください")
	@Pattern(regexp = "^[0-9]{4}$", message = "店舗コードは半角数字4桁で入力してください。")
	private String tenpoCd;

	/**
	 * セット商品マスタ選択用チェックボックス
	 */
	private boolean check;

	/**
	 * 更新度数
	 */
	private Long version;
}
