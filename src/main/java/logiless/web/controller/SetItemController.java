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
import logiless.web.model.dto.SetItem;
import logiless.web.model.dto.Tenpo;
import logiless.web.model.form.SetItemForm;
import logiless.web.model.form.SetItemListForm;
import logiless.web.model.service.SetItemService;

/**
 * セット商品マスター関連画面コントローラー
 * 
 * @author nsh14789
 *
 */
@Controller
public class SetItemController {

	private final SetItemService setItemService;

	@Autowired
	public SetItemController(SetItemService setItemService) {
		this.setItemService = setItemService;
	}

	/**
	 * セット商品マスター画面表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/setItem/master/list")
	public String getSetItemMasterList(Model model,
			@RequestParam(name = "tenpoCode", required = false) String tenpoCode,
			@RequestParam(name = "setItemCode", required = false) String setItemCode,
			@RequestParam(name = "setItemName", required = false) String setItemName) {

		List<Tenpo> tenpoList = setItemService.getAllTenpoList();
		model.addAttribute("tenpoList", tenpoList);

		SetItemListForm setItemListForm = new SetItemListForm();

		if (tenpoCode != null) {

			setItemListForm.setSetItemList(
					setItemService.getSetItemListByCodeAndNameLikeAndTenpoCode(setItemCode, setItemName, tenpoCode));
			model.addAttribute("setItemListForm", setItemListForm);
		} else {

			setItemListForm.setSetItemList(new ArrayList<SetItem>());
			model.addAttribute("setItemListForm", setItemListForm);
		}

		model.addAttribute("tenpoCode", tenpoCode);
		model.addAttribute("setItemCode", setItemCode);
		model.addAttribute("setItemName", setItemName);

		return "setItem/master/list";
	}

	/**
	 * セット商品マスター詳細画面<br>
	 * 店舗コードとセット商品コードがパラムに含まれている場合、編集画面<br>
	 * 含まれていない場合、新規登録画面
	 * 
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

		// 新規登録と編集を判別するためのフラグ
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

	/**
	 * セット商品情報登録
	 * 
	 * @param model
	 * @param setItemForm
	 * @return
	 */
	@PostMapping("/setItem/master/submit")
	public String setItemMasterSubmit(Model model, @Param("setItemForm") SetItemForm setItemForm) {

		boolean result = setItemService.updateSetItem(setItemForm);
		if (!result) {
			model.addAttribute("message", "エラー：登録に失敗しました。");
		} else {
			model.addAttribute("message", "登録が完了しました");
		}

		List<Tenpo> tenpoList = setItemService.getAllTenpoList();
		model.addAttribute("tenpoList", tenpoList);

		return "redirect:/setItem/master/list";
	}

	/**
	 * セット商品情報削除
	 * 
	 * @param model
	 * @param setItemForm
	 * @return
	 */
	@PostMapping("/setItem/master/delete")
	public String setItemMasterDelete(Model model, @Param("setItemListForm") SetItemListForm setItemListForm) {

		boolean result = setItemService.deleteSetItem(setItemListForm);
		if (!result) {
			model.addAttribute("message", "エラー登録に失敗しました。");
		} else {
			model.addAttribute("message", "登録が完了しました");
		}

		List<Tenpo> tenpoList = setItemService.getAllTenpoList();
		model.addAttribute("tenpoList", tenpoList);

		return "redirect:/setItem/master/list";
	}

	/**
	 * セット商品情報一括登録画面初期遷移
	 * 
	 * @return
	 */
	@GetMapping("/setItem/upload")
	public String setItemUpload() {
		return "setItem/upload/index";
	}

	/**
	 * * セット商品情報一括登録完了画面
	 * 
	 * @return
	 */
	@GetMapping("/setItem/upload/complete")
	public String setItemUploadComplete() {
		return "setItem/upload/complete";
	}

}
