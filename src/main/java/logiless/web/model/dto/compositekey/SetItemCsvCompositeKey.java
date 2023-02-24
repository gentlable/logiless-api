package logiless.web.model.dto.compositekey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * SetItemCsvのリストをグループ化するときの複合キー
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SetItemCsvCompositeKey {

	private String tenpoCode;

	private String setItemCode;

}
