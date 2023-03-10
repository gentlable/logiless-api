package logiless.common.model.dto.syohn;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ロジレス商品
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@ToString
public class Article {

	private String id;
	private String code;
	private String identificationCode;
	private String objectCode;
	private String modelNumber;
	private String name;
	private String nameKana;

}
