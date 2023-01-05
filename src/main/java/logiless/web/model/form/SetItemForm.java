package logiless.web.model.form;

import java.util.List;

import logiless.web.model.dto.BaraItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetItemForm {
	
	private List<BaraItem> baraItemList;
}
