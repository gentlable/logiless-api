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

	private String code;
	private String tenpoCode;

	@Override
	public void initialize(SetItemExist annotation) {
		this.code = annotation.code();
		this.tenpoCode = annotation.tenpoCode();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {

		BeanWrapper beanWrapper = new BeanWrapperImpl(object);
		String code = (String) beanWrapper.getPropertyValue(this.code);
		String tenpoCode = (String) beanWrapper.getPropertyValue(this.tenpoCode);

		SetItem setItem = setItemService.getSetItemByCodeAndTenpoCode(code, tenpoCode);

		if (setItem == null) {
			return false;
		}

		return true;

	}

}
