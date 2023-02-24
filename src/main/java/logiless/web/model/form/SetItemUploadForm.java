package logiless.web.model.form;

import org.springframework.web.multipart.MultipartFile;

import logiless.web.validator.FileRequired;
import lombok.Getter;
import lombok.Setter;

/**
 * セット商品一括登録フォーム
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
public class SetItemUploadForm {

	@FileRequired(message = "ファイルを選択してください")
	public MultipartFile file;

}
