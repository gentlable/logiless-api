package logiless.web.model.form;

import java.util.List;

import javax.validation.Valid;

import logiless.web.model.dto.Tenpo;
import lombok.Getter;
import lombok.Setter;

/**
 * 店舗一覧フォーム
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
public class TenpoListForm {

	@Valid
	private List<Tenpo> tenpoList;

}
