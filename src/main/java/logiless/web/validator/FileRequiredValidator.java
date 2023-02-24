package logiless.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

/**
 * ファイル必須チェック実装
 * 
 * @author nsh14789
 *
 */
public class FileRequiredValidator implements ConstraintValidator<FileRequired, MultipartFile> {

	@Override
	public void initialize(FileRequired constraint) {
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		return multipartFile != null && !multipartFile.getOriginalFilename().isEmpty();
	}

}
