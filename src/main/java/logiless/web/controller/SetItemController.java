package logiless.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import logiless.web.model.dto.BaraItem;
import logiless.web.model.dto.SetItem;
import logiless.web.model.dto.Tenpo;
import logiless.web.model.form.SetItemForm;
import logiless.web.model.form.SetItemListForm;
import logiless.web.model.form.SetItemSearchForm;
import logiless.web.model.service.SetItemService;

/**
 * セット商品マスター関連画面コントローラー
 * 
 * @author nsh14789
 *
 */
@Controller
@SessionAttributes(value = { "tenpoList", "setItemForm" })
public class SetItemController {

	private final SetItemService setItemService;

	@InitBinder
	public void initbinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Autowired
	public SetItemController(SetItemService setItemService) {
		this.setItemService = setItemService;
	}

	@ModelAttribute(value = "tenpoList")
	public List<Tenpo> createTenpoList() {
		return setItemService.getAllTenpoList();
	}

	@ModelAttribute(value = "setItemForm")
	public SetItemForm createSetItemForm() {
		return new SetItemForm();
	}

	@ModelAttribute(value = "setItemSearchForm")
	public SetItemSearchForm createSetItemSearchForm() {
		return new SetItemSearchForm();
	}

	/**
	 * セット商品マスター画面初期表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/setItem/master/list/init")
	public String getSetItemMasterListInit(Model model,
			@ModelAttribute("setItemSearchForm") SetItemSearchForm setItemSearchForm) {

		List<Tenpo> tenpoList = setItemService.getAllTenpoList();
		model.addAttribute("tenpoList", tenpoList);

		SetItemListForm setItemListForm = new SetItemListForm();
		setItemListForm.setSetItemList(new ArrayList<SetItem>());
		model.addAttribute("setItemListForm", setItemListForm);
		return "setItem/master/list";
	}

	/**
	 * セット商品マスター画面表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/setItem/master/list")
	public String getSetItemMasterList(Model model,
			@Valid @ModelAttribute("setItemSearchForm") SetItemSearchForm setItemSearchForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			SetItemListForm setItemListForm = new SetItemListForm();
			setItemListForm.setSetItemList(new ArrayList<SetItem>());
			model.addAttribute("setItemListForm", setItemListForm);
			return "setItem/master/list";
		}

		SetItemListForm setItemListForm = new SetItemListForm();

		setItemListForm.setSetItemList(
				setItemService.getSetItemListByCodeAndNameLikeAndTenpoCode(setItemSearchForm.getSetItemCode(),
						setItemSearchForm.getSetItemName(), setItemSearchForm.getTenpoCode()));
		model.addAttribute("setItemListForm", setItemListForm);
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

		@SuppressWarnings("unchecked")
		List<Tenpo> tenpoList = (List<Tenpo>) model.getAttribute("tenpoList");

		SetItemForm setItemForm = new SetItemForm();
		List<BaraItem> baraItemList = new ArrayList<BaraItem>();
		baraItemList.add(new BaraItem());
		setItemForm.setBaraItemList(baraItemList);

		if (tenpoCode != null && setItemCode != null) {
			setItemForm.setSetItem(setItemService.getSetItemByCodeAndTenpoCode(setItemCode, tenpoCode));
			setItemForm.setBaraItemList(setItemService.getBaraItemByTenpoCodeAndSetItemCode(tenpoCode, setItemCode));
			setItemForm.setEditFlg(true);

			for (Tenpo tenpo : tenpoList) {
				if (tenpoCode.equals(tenpo.getCode())) {
					setItemForm.setTenpoName(tenpo.getName());
					break;
				}
			}
		}

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
	public String setItemMasterSubmit(Model model, @Valid @ModelAttribute("setItemForm") SetItemForm setItemForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "setItem/master/detail";
		}

		boolean result = setItemService.updateSetItem(setItemForm);
		if (!result) {
			model.addAttribute("message", "エラー：登録に失敗しました。");
			return "setItem/master/detail";
		} else {
			model.addAttribute("message", "登録が完了しました");
		}

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
			return "setItem/master/list";
		} else {
			model.addAttribute("message", "登録が完了しました");
		}

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
