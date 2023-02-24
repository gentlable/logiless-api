package logiless.common.model.dto.sykka;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ロジレス出荷実在庫
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@ToString
public class ShippedAcutalStock {

	private String articleCode;
	private String deadline;
	private String lotNumber;
	private String quantity;

}
