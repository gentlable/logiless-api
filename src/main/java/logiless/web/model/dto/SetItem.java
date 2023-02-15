package logiless.web.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetItem {
	@NotBlank
	@Pattern(regexp = "^[0-9]{10}$")
	private String code;

	private String name;

	@NotBlank
	@Pattern(regexp = "^[0-9]{4}$")
	private String tenpoCode;

	/**
	 * セット商品マスタ選択用チェックボックス
	 */
	private boolean check;
}
