package logiless.web.model.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

/**
 * バラ商品DTO
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
public class BaraItem {

	@Pattern(regexp = "^[0-9]{10}$", message = "バラ商品コードは半角数字10桁で入力してください")
	private String code;

	private String name;

	@Pattern(regexp = "^[0-9]{4}$", message = "店舗コードは半角数字4桁で入力してください")
	private String tenpoCode;

	@Pattern(regexp = "^[0-9]{10}$", message = "セット商品コードは半角数字10桁で入力してください")
	private String setItemCode;

	@NotNull
	@PositiveOrZero(message = "数量は0以上を入力してください")
	@Max(value = 99999, message = "数量は99999以下を入力してください")
	private int quantity;

	@NotNull
	@PositiveOrZero(message = "単価は0以上を入力してください")
	@DecimalMax(value = "99999999.99", message = "単価は99999999.99以下を入力してください")
	@Digits(integer = 8, fraction = 2, message = "単価は整数部8桁、小数部2桁以内で入力してください")
	private double price;
}
