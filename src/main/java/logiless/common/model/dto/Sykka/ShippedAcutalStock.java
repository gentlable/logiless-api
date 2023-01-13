package logiless.common.model.dto.Sykka;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShippedAcutalStock {

	private String articleCode;
	private String deadline;
	private String lotNumber;
	private String quantity;
	
}
