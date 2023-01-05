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

@Controller
public class DemoController {

	@Autowired
	TenpoService tenpoService;

	@GetMapping("/demo/edit")
	private String getDemoEdit(Model model) {

		return "demo/edit";
	}

	@PostMapping("/demo/edit")
	private String postDemoEdit(Model model, @ModelAttribute Tenpo tenpo) {

		boolean result = tenpoService.insertTenpo(tenpo);
		if(result) {
			model.addAttribute("message", "登録成功しました。");
		} else {
			model.addAttribute("message", "登録失敗しました。");
		}
		return "demo/edit";
	}

	@GetMapping("/demo/editTenpoList")
	private String getDemoEditTenpoList(Model model) {
		List<Tenpo> tenpoList = new ArrayList<Tenpo>();
		tenpoList.add(new Tenpo());
		TenpoListForm tenpoListForm = new TenpoListForm();
		tenpoListForm.setTenpoList(tenpoList);
		model.addAttribute("tenpoListForm", tenpoListForm);
		return "demo/editTenpoList";
	}

	@PostMapping("/demo/editTenpoList")
	private String postDemoEditTenpoList(Model model, @ModelAttribute TenpoListForm tenpoListForm) {

		boolean result = tenpoService.insertTenpoList(tenpoListForm);
		if(result) {
			model.addAttribute("message", "登録成功しました。");
		} else {
			model.addAttribute("message", "登録失敗しました。");
		}
		return "demo/editTenpoList";
	}

}
