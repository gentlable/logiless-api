package logiless.web.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetItemCsv {

	@JsonProperty(value = "店舗コード", index = 0)
	@Pattern(regexp = "^[0-9]{4}$", message = "店舗コードは半角数字4桁で入力してください。")
	private String tenpoCd;

	@JsonProperty(value = "店舗名", index = 1)
	private String tenpoNm;

	@JsonProperty(value = "セット商品コード", index = 2)
	@Pattern(regexp = "^[0-9]{10}$", message = "セット商品コードは半角数字10桁で入力してください。")
	private String setItemCd;

	@JsonProperty(value = "セット商品名", index = 3)
	@NotEmpty(message = "セット商品名を入力してください。")
	private String setItemNm;

	@JsonProperty(value = "バラ商品コード", index = 4)
	@Pattern(regexp = "^[0-9]{10}$", message = "バラ商品コードは半角数字10桁で入力してください。")
	private String baraItemCd;

	@JsonProperty(value = "バラ商品名", index = 5)
	@NotEmpty(message = "バラ商品名を入力してください。")
	private String baraItemNm;

	@JsonProperty(value = "数量", index = 6)
	private int quantity;

	@JsonProperty(value = "単価", index = 7)
	private double price;

}
