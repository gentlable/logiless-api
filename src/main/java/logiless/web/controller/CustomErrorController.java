package logiless.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * エラー時のコントローラー
 * 
 * @author nsh14789
 *
 */
@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());
			model.addAttribute("errorCode", statusCode);

			// ステータスに応じて処理
			if (status == HttpStatus.NOT_FOUND) {
				// 404 Not Found
				model.addAttribute("errorMessage", "ページが見つかりません。");
			} else {
				// 404 以外は500 Internal Server Error とする
				model.addAttribute("errorMessage", "システムエラーが発生しました。システム管理者にお問い合わせ下さい。");
			}
		}
		return "error";
	}

	/**
	 * レスポンス用の HTTP ステータスを決める。
	 *
	 * @param req リクエスト情報
	 * @return レスポンス用 HTTP ステータス
	 */
	private static HttpStatus getHttpStatus(HttpServletRequest req) {
		// HTTP ステータスを決める
		// ここでは 404 以外は全部 500 にする
		Object statusCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (statusCode != null && statusCode.toString().equals("404")) {
			status = HttpStatus.NOT_FOUND;
		}
		return status;
	}//
//	@Override
//	public String getErrorPath() {
//		return "/error";
//	}
}
