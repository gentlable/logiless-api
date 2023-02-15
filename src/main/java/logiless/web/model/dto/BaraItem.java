package logiless.web.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaraItem {
	@NotBlank
	@Pattern(regexp = "^[0-9]{10}$")
	private String code;

	private String name;

	@NotBlank
	@Pattern(regexp = "^[0-9]{4}$")
	private String tenpoCode;

	@NotBlank
	@Pattern(regexp = "^[0-9]{4}$")
	private String setItemCode;

	@NotNull
	@PositiveOrZero
	private int quantity;

	@NotNull
	@PositiveOrZero
	private double price;
}
