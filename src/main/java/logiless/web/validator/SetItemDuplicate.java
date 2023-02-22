package logiless.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SetItemDuplicateValidator.class)
public @interface SetItemDuplicate {

	String message() default "入力されたセット商品は既に存在しています。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String code();

	String tenpoCode();

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		SetItemDuplicate[] values();
	}

}
