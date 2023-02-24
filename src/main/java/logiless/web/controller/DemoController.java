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
import logiless.web.model.entity.TenpoEntity;
import logiless.web.model.form.TenpoListForm;
import logiless.web.model.repository.TenpoRepository;
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

	private final TenpoRepository tenpoRepository;

	@Autowired
	public DemoController(TenpoService tenpoService, TenpoRepository tenpoRepository) {
		this.tenpoService = tenpoService;
		this.tenpoRepository = tenpoRepository;
	}

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

	/**
	 * DB編集デモ、店舗マスタを編集できる
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/demo/edit")
	public String getDemoEdit(Model model) {

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
	public String postDemoEdit(Model model, @ModelAttribute Tenpo tenpo) {

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
	public String getDemoEditTenpoList(Model model) {

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
	public String postDemoEditTenpoList(Model model, @ModelAttribute TenpoListForm tenpoListForm) {

		boolean result = tenpoService.insertTenpoList(tenpoListForm);
		if (result) {
			model.addAttribute("message", "登録成功しました。");
		} else {
			model.addAttribute("message", "登録失敗しました。");
		}
		return "demo/editTenpoList";
	}

	/**
	 * jpqlデモ（店舗取得）
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/demo/jpql")
	public String getDemoJpql() {

		List<TenpoEntity> test = tenpoRepository.jqplDemo("5676");

		return "demo/index";

	}

}
