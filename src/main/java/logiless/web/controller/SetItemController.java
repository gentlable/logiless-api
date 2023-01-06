package logiless.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import logiless.web.model.dto.BaraItem;
import logiless.web.model.dto.Tenpo;
import logiless.web.model.form.SetItemForm;
import logiless.web.model.service.SetItemService;

@Controller
public class SetItemController {

	@Autowired
	private SetItemService setItemService;

	/**
	 * セット商品マスター画面初期表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/setItem/master/list")
	public String getSetItemMasterList(Model model,
			@RequestParam(name = "tenpoCode", required = false) String tenpoCode,
			@RequestParam(name = "setItemCode", required = false) String setItemCode,
			@RequestParam(name = "name", required = false) String name) {

		List<Tenpo> tenpoList = setItemService.getAllTenpoList();
		model.addAttribute("tenpoList", tenpoList);

		if ((tenpoCode != null) || (setItemCode != null) || (name != null)) {
			model.addAttribute("resultList", setItemService.getSetItemListByTenpoCode(tenpoCode));
		}

		return "setItem/master/list";
	}

	/**
	 * セット商品マスター詳細画面
	 * 店舗コードとセット商品コードがパラムに含まれている場合、編集画面
	 * @param model
	 * @param setItemCode
	 * @param tenpoCode
	 * @return
	 */
	@GetMapping("/setItem/master/detail")
	public String setItemMasterDetail(Model model,
			@RequestParam(name = "setItemCode", required = false) String setItemCode,
			@RequestParam(name = "tenpoCode", required = false) String tenpoCode) {

		List<Tenpo> tenpoList = setItemService.getAllTenpoList();
		model.addAttribute("tenpoList", tenpoList);
		
		SetItemForm setItemForm = new SetItemForm();
		List<BaraItem> baraItemList = new ArrayList<BaraItem>();
		baraItemList.add(new BaraItem());
		setItemForm.setBaraItemList(baraItemList);
		
		boolean editFlg = false;

		if (tenpoCode != null && setItemCode != null) {
			setItemForm.setSetItem(setItemService.getSetItemByCodeAndTenpoCode(setItemCode, tenpoCode));
			setItemForm.setBaraItemList(setItemService.getBaraItemByTenpoCodeAndSetItemCode(tenpoCode, setItemCode));
			editFlg = true;
		}
		model.addAttribute("editFlg", editFlg);
		model.addAttribute("tenpoCode", tenpoCode);
		model.addAttribute("setItemForm", setItemForm);
		
		return "setItem/master/detail";
	}
	
	@PostMapping("/setItem/master/submit")
	public String setItemMasterSubmit(Model model,
			@Param("setItemForm") SetItemForm setItemForm) {

		model.addAttribute("message", "登録が完了しました");

		return "setItem/master/list";
	}

	@GetMapping("/setItem/upload")
	public String setItemUpload() {
		return "setItem/upload/index";
	}

	@GetMapping("/setItem/upload/complete")
	public String setItemUploadComplete() {
		return "setItem/upload/complete";
	}

}
