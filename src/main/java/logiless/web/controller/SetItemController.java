package logiless.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import logiless.web.model.dto.SetItem;
import logiless.web.model.dto.Tenpo;
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

	@GetMapping("/setItem/master/detail")
	public String setItemMasterDetail(Model model,
			@RequestParam(name = "setItemCode", required = false) String setItemCode,
			@RequestParam(name = "tenpoCode", required = true) String tenpoCode) {

		List<Tenpo> storeList = setItemService.getAllTenpoList();
		model.addAttribute("storeList", storeList);

		if (tenpoCode != null && setItemCode != null) {
			
			SetItem setItem = setItemService.getSetItemByCodeAndTenpoCode(setItemCode, tenpoCode);
			
			model.addAttribute("tenpoCode", tenpoCode);
			model.addAttribute("setItemCode", setItemCode);
			model.addAttribute("setItemName", setItem.getName());
			model.addAttribute("resultList", setItemService.getBaraItemByTenpoCodeAndSetItemCode(tenpoCode, setItemCode));
		}

		return "setItem/master/detail";
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
