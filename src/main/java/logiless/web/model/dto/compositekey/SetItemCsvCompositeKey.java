package logiless.web.model.dto.compositekey;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class SetItemCsvCompositeKey {

	private String tenpoCd;

	private String setItemCd;

}
