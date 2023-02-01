package logiless.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import logiless.web.model.dto.Tenpo;
import logiless.web.model.form.TenpoListForm;
import logiless.web.model.service.TenpoService;

/**
 * デモページ用コントローラー
 * 
 * @author nsh14789
 *
 */
@Controller
public class DemoController {

	private final TenpoService tenpoService;

	@Autowired
	public DemoController(TenpoService tenpoService) {
		this.tenpoService = tenpoService;
	}

	/**
	 * デモページメニュー
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/demo")
	private String getDemo(Model model) {

		return "demo/index";
	}

	/**
	 * DB編集デモ、店舗マスタを編集できる
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/demo/edit")
	private String getDemoEdit(Model model) {

		// TODO 店舗取得してモデルにセット

		return "demo/edit";
	}

	/**
	 * 店舗マスタ登録
	 * 
	 * @param model
	 * @param tenpo
	 * @return
	 */
	@PostMapping("/demo/edit")
	private String postDemoEdit(Model model, @ModelAttribute Tenpo tenpo) {

		boolean result = tenpoService.insertTenpo(tenpo);
		if (result) {
			model.addAttribute("message", "登録成功しました。");
		} else {
			model.addAttribute("message", "登録失敗しました。");
		}
		return "demo/edit";
	}

	/**
	 * 複数店舗編集画面へ遷移
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/demo/editTenpoList")
	private String getDemoEditTenpoList(Model model) {

		List<Tenpo> tenpoList = new ArrayList<Tenpo>();
		tenpoList.add(new Tenpo());
		TenpoListForm tenpoListForm = new TenpoListForm();
		tenpoListForm.setTenpoList(tenpoList);
		model.addAttribute("tenpoListForm", tenpoListForm);
		return "demo/editTenpoList";
	}

	/**
	 * 複数店舗編集登録
	 * 
	 * @param model
	 * @param tenpoListForm
	 * @return
	 */
	@PostMapping("/demo/editTenpoList")
	private String postDemoEditTenpoList(Model model, @ModelAttribute TenpoListForm tenpoListForm) {

		boolean result = tenpoService.insertTenpoList(tenpoListForm);
		if (result) {
			model.addAttribute("message", "登録成功しました。");
		} else {
			model.addAttribute("message", "登録失敗しました。");
		}
		return "demo/editTenpoList";
	}

}
