package logiless.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * デモページ用コントローラー
 * 
 * @author nsh14789
 *
 */
@Controller
public class DemoController {

	/**
	 * デモページメニュー
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/demo")
	public String getDemo(Model model) {

		return "demo/index";
	}

}
