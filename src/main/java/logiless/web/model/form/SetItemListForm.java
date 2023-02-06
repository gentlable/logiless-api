package logiless.web.model.form;

import java.util.List;

import javax.validation.Valid;

import logiless.web.model.dto.SetItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetItemListForm {
	@Valid
	private List<SetItem> setItemList;

}