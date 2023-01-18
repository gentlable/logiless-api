package logiless.common.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Constant {
	
	/**
	 * カテゴリコード
	 * @author nsh14789
	 *
	 */
	@Getter
	@AllArgsConstructor
	public enum CATEGORY_CD {
		
		GENDER("00001");
		private String value;
	}
	
}
