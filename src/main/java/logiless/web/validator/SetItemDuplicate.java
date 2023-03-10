package logiless.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * セット商品重複チェックアノテーション<br>
 * 店舗コードとセット商品コードをキーにセット商品の重複チェックを行う
 * 
 * @author nsh14789
 *
 */
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SetItemDuplicateValidator.class)
public @interface SetItemDuplicate {

	String message() default "入力されたセット商品は既に存在しています。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String setItemCd();

	String tenpoCd();

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		SetItemDuplicate[] values();
	}

}
