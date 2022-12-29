package logiless.web.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tenpo {
	
	@JsonProperty("id")
	private String code;
	
	private String name;

}
