package logiless.web.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import logiless.web.model.dto.BaraItem;

/**
 * バラ商品重複チェック実装
 * 
 * @author nsh14789
 *
 */
public class BaraItemDuplicateValidator implements ConstraintValidator<BaraItemDuplicate, List<BaraItem>> {

	@Override
	public void initialize(BaraItemDuplicate annotation) {
	}

	@Override
	public boolean isValid(List<BaraItem> baraItemList, ConstraintValidatorContext context) {

		if (baraItemList == null) {
			return true;
		}

		Set<String> baraItemCdSet = new HashSet<>();
		for (BaraItem baraItem : baraItemList) {

			String baraItemCd = baraItem.getBaraItemCd();

			if (baraItemCdSet.contains(baraItemCd)) {
				return false;
			}
			baraItemCdSet.add(baraItemCd);
		}

		return true;

	}

}
