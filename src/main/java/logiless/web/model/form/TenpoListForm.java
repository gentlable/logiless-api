package logiless.web.model.form;

import java.util.List;

import javax.validation.Valid;

import logiless.web.model.dto.Tenpo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenpoListForm {
	
	@Valid
	private List<Tenpo> tenpoList;

}
