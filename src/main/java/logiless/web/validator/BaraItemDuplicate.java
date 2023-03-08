package logiless.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * バラ商品重複チェックアノテーション<br>
 * セット商品登録時、バラ商品コードが重複して入力されていないかチェックする
 * 
 * @author nsh14789
 *
 */
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BaraItemDuplicateValidator.class)
public @interface BaraItemDuplicate {

	String message() default "バラ商品コードが重複しています。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		BaraItemDuplicate[] values();
	}

}
