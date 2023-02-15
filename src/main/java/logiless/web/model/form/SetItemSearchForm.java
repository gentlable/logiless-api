package logiless.web.model.form;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetItemSearchForm {

	@NotEmpty(message = "店舗を選択してください")
	@Valid
	public String tenpoCode;

	@Valid
	@Size(min = 10, max = 10, message = "セット商品コードは10桁で入力してください。")
	public String setItemCode;

	@Valid
	public String setItemName;
}
