package logiless.web.model.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * セット商品検索フォーム
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
public class SetItemSearchForm {

	@NotEmpty(message = "店舗を選択してください")
	public String tenpoCode;

	@Pattern(regexp = "^[0-9]{10}$", message = "セット商品コードは半角数字10桁で入力してください。")
	public String setItemCode;

	public String setItemName;
}
