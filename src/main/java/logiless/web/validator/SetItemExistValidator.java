package logiless.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import logiless.web.model.dto.SetItem;
import logiless.web.model.service.SetItemService;

/**
 * セット商品存在チェック実装
 * 
 * @author nsh14789
 *
 */
public class SetItemExistValidator implements ConstraintValidator<SetItemExist, Object> {

	private final SetItemService setItemService;

	@Autowired
	public SetItemExistValidator(SetItemService setItemService) {
		this.setItemService = setItemService;
	}

	private String setItemCd;
	private String tenpoCd;

	@Override
	public void initialize(SetItemExist annotation) {
		this.setItemCd = annotation.setItemCd();
		this.tenpoCd = annotation.tenpoCd();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {

		BeanWrapper beanWrapper = new BeanWrapperImpl(object);
		String setItemCd = (String) beanWrapper.getPropertyValue(this.setItemCd);
		String tenpoCd = (String) beanWrapper.getPropertyValue(this.tenpoCd);

		SetItem setItem = setItemService.getSetItemBySetItemCdAndTenpoCd(setItemCd, tenpoCd);

		if (setItem == null) {
			return false;
		}

		return true;

	}

}
