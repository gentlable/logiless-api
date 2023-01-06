package logiless.web.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tenpo {

	@NotBlank
	@Pattern(regexp = "^[0-9]{4}$")
	@JsonProperty("id")
	private String code;

	@NotBlank
	private String name;

}
