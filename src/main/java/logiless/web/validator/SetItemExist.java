package logiless.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * セット商品存在チェックアノテーション<br>
 * 店舗コードとセット商品コードをキーにセット商品の存在チェックを行う
 * 
 * @author nsh14789
 *
 */
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SetItemExistValidator.class)
public @interface SetItemExist {

	String message() default "入力されたセット商品は存在しません。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String setItemCd();

	String tenpoCd();

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		SetItemExist[] values();
	}

}
