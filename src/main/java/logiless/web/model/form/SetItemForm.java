package logiless.web.model.form;

import java.util.List;

import javax.validation.Valid;

import logiless.web.model.dto.BaraItem;
import logiless.web.model.dto.SetItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetItemForm {
	@Valid
	private SetItem setItem;
	@Valid
	private List<BaraItem> baraItemList;
}
