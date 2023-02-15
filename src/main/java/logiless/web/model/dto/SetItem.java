package logiless.web.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetItem {

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
