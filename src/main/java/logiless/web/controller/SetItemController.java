package logiless.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import logiless.common.exception.InvalidInputException;
import logiless.web.model.dto.BaraItem;
import logiless.web.model.dto.SetItem;
import logiless.web.model.dto.SetItem.InsertData;
import logiless.web.model.dto.SetItem.UpdateData;
import logiless.web.model.dto.SetItemCsv;
import logiless.web.model.dto.Tenpo;
import logiless.web.model.form.SetItemForm;
import logiless.web.model.form.SetItemListForm;
import logiless.web.model.form.SetItemSearchForm;
import logiless.web.model.form.SetItemUploadForm;
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

	private final Validator validator;
	private final SetItemService setItemService;

	/**
	 * リクエストの内空文字はnullに変換する
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initbinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Autowired
	public SetItemController(Validator validator, SetItemService setItemService) {
		this.validator = validator;
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

	@ModelAttribute(value = "setItemUploadForm")
	public SetItemUploadForm createSetItemUploadForm() {
		return new SetItemUploadForm();
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

		setItemListForm.setSetItemList(setItemService.getSetItemListBySetItemCdAndSetItemNmLikeAndTenpoCd(
				setItemSearchForm.getSetItemCd(), setItemSearchForm.getSetItemNm(), setItemSearchForm.getTenpoCd()));
		model.addAttribute("setItemListForm", setItemListForm);
		return "setItem/master/list";
	}

	/**
	 * セット商品マスターCSV出力
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/setItem/master/list/csv", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
			+ "; charset=UTF-8; Content-Disposition: attachment")
	@ResponseBody
	public Object getSetItemMasterListCsv(Model model,
			@Valid @ModelAttribute("setItemSearchForm") SetItemSearchForm setItemSearchForm,
			BindingResult bindingResult) throws Exception {

		if (bindingResult.hasErrors()) {
			SetItemListForm setItemListForm = new SetItemListForm();
			setItemListForm.setSetItemList(new ArrayList<SetItem>());
			model.addAttribute("setItemListForm", setItemListForm);
			return ResponseEntity.notFound().build();
		}

		List<SetItem> setItemList = setItemService.getSetItemListBySetItemCdAndSetItemNmLikeAndTenpoCd(
				setItemSearchForm.getSetItemCd(), setItemSearchForm.getSetItemNm(), setItemSearchForm.getTenpoCd());

		CsvMapper csvMapper = new CsvMapper();
		csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
		csvMapper.enable(CsvGenerator.Feature.STRICT_CHECK_FOR_QUOTING);
		csvMapper.getFactory().configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, false);
		csvMapper.getFactory().configure(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE, true);
		CsvSchema schema = csvMapper.schemaFor(SetItemCsv.class).withHeader();

		List<SetItemCsv> setItemCsvList = new ArrayList<SetItemCsv>();

		@SuppressWarnings("unchecked")
		List<Tenpo> tenpoList = (List<Tenpo>) model.getAttribute("tenpoList");

		for (SetItem setItem : setItemList) {

			// 店舗取得
			Tenpo tenpo = tenpoList.stream().filter(obj -> obj.getTenpoCd().equals(setItem.getTenpoCd())).findFirst()
					.orElse(new Tenpo());

			List<BaraItem> baraItemList = setItemService.getBaraItemByTenpoCdAndSetItemCd(setItem.getTenpoCd(),
					setItem.getSetItemCd());

			for (BaraItem baraItem : baraItemList) {

				SetItemCsv setItemCsv = new SetItemCsv();

				setItemCsv.setTenpoCd(setItem.getTenpoCd());
				setItemCsv.setTenpoNm(tenpo.getTenpoNm());
				setItemCsv.setSetItemCd(setItem.getSetItemCd());
				setItemCsv.setSetItemNm(setItem.getSetItemNm());
				setItemCsv.setBaraItemCd(baraItem.getBaraItemCd());
				setItemCsv.setBaraItemNm(baraItem.getBaraItemNm());
				setItemCsv.setQuantity(baraItem.getQuantity());
				setItemCsv.setPrice(baraItem.getPrice());

				setItemCsvList.add(setItemCsv);
			}
		}

		try {
			String body = csvMapper.writer(schema).writeValueAsString(setItemCsvList);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"SetSyohn_" + setItemSearchForm.getTenpoCd() + ".csv\"")
					.contentType(MediaType.TEXT_PLAIN).body(body);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * セット商品マスター詳細画面<br>
	 * 店舗コードとセット商品コードがパラムに含まれている場合、編集画面<br>
	 * 含まれていない場合、新規登録画面
	 * 
	 * @param model
	 * @param setItemCd
	 * @param tenpoCd
	 * @return
	 */
	@GetMapping("/setItem/master/detail")
	public String setItemMasterDetail(Model model, @RequestParam(name = "setItemCd", required = false) String setItemCd,
			@RequestParam(name = "tenpoCd", required = false) String tenpoCd) {

		@SuppressWarnings("unchecked")
		List<Tenpo> tenpoList = (List<Tenpo>) model.getAttribute("tenpoList");

		SetItemForm setItemForm = new SetItemForm();
		List<BaraItem> baraItemList = new ArrayList<BaraItem>();
		baraItemList.add(new BaraItem());
		setItemForm.setBaraItemList(baraItemList);

		// セット商品情報がgetパラメータで送られてきた場合は更新の設定をする
		if (tenpoCd != null && setItemCd != null) {
			setItemForm.setSetItem(setItemService.getSetItemBySetItemCdAndTenpoCd(setItemCd, tenpoCd));
			setItemForm.setBaraItemList(setItemService.getBaraItemByTenpoCdAndSetItemCd(tenpoCd, setItemCd));
			setItemForm.setEditFlg(true);

			// 店舗名セット
			for (Tenpo tenpo : tenpoList) {
				if (tenpoCd.equals(tenpo.getTenpoCd())) {
					setItemForm.setTenpoNm(tenpo.getTenpoNm());
					break;
				}
			}

			// 次の画面のアクションURLを設定
			model.addAttribute("action", "update");

		} else {
			// 次の画面のアクションURLを設定
			model.addAttribute("action", "insert");
		}

		model.addAttribute("tenpoCd", tenpoCd);
		model.addAttribute("setItemForm", setItemForm);

		return "setItem/master/detail";
	}

	/**
	 * セット商品情報新規登録
	 * 
	 * @param model
	 * @param setItemForm
	 * @return
	 */
	@PostMapping("/setItem/master/insert")
	public String setItemMasterInsert(Model model,
			@Valid @Validated(InsertData.class) @ModelAttribute("setItemForm") SetItemForm setItemForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("action", "insert");
			return "setItem/master/detail";
		}

		Set<ConstraintViolation<SetItemForm>> violations = validator.validate(setItemForm, InsertData.class);

		if (!violations.isEmpty()) {

			// バリデーションに問題がある場合は、ログにエラーメッセージを出力する
			for (ConstraintViolation<SetItemForm> violation : violations) {
				String message = violation.getMessage();
				model.addAttribute("message", message);
				return "setItem/master/detail";
			}
		}

		boolean result = setItemService.insertSetItem(setItemForm);

		if (!result) {
			model.addAttribute("message", "エラー：登録に失敗しました。");
			return "setItem/master/detail";
		} else {
			model.addAttribute("message", "登録が完了しました");
		}

		return "setItem/master/complete";
	}

	/**
	 * セット商品情報更新
	 * 
	 * @param model
	 * @param setItemForm
	 * @return
	 */
	@PostMapping("/setItem/master/update")
	public String setItemMasterUpdate(Model model,
			@Valid @Validated(UpdateData.class) @ModelAttribute("setItemForm") SetItemForm setItemForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("action", "update");
			return "setItem/master/detail";
		}

		Set<ConstraintViolation<SetItemForm>> violations = validator.validate(setItemForm, UpdateData.class);

		if (!violations.isEmpty()) {

			// バリデーションに問題がある場合は、ログにエラーメッセージを出力する
			for (ConstraintViolation<SetItemForm> violation : violations) {
				String message = violation.getMessage();
				model.addAttribute("message", message);
				return "setItem/master/detail";
			}
		}

		boolean result = false;

		try {
			result = setItemService.updateSetItem(setItemForm);
		} catch (NoResultException e) {
			model.addAttribute("message", "エラー：更新対象が見つかりません。");
			return "setItem/master/detail";
		}

		if (!result) {
			model.addAttribute("message", "エラー：登録に失敗しました。");
			return "setItem/master/detail";
		} else {
			model.addAttribute("message", "登録が完了しました");
		}

		return "setItem/master/complete";
	}

	/**
	 * セット商品情報削除
	 * 
	 * @param model
	 * @param setItemForm
	 * @return
	 */
	@PostMapping("/setItem/master/delete")
	public String setItemMasterDelete(Model model, @Param("setItemListForm") SetItemListForm setItemListForm,
			RedirectAttributes redirectAttributes) {

		boolean result = setItemService.deleteSetItem(setItemListForm);
		if (!result) {
			redirectAttributes.addAttribute("message", "エラー削除に失敗しました。");
			return "setItem/master/list";
		} else {
			redirectAttributes.addAttribute("message", "登録が完了しました");
		}

		return "setItem/master/complete";
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
	 * セット商品情報一括登録処理
	 * 
	 * @return
	 */
	@PostMapping("/setItem/upload/submit")
	public String setItemUploadSubmit(Model model,
			@Valid @ModelAttribute("setItemUploadForm") SetItemUploadForm setItemUploadForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "setItem/upload/index";
		}

		boolean result = false;

		try {
			result = setItemService.uploadSetItem(setItemUploadForm);
		} catch (InvalidInputException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "setItem/upload/index";
		}

		if (!result) {
			model.addAttribute("errorMessage", "エラーが発生しました");
			return "setItem/upload/index";
		}

		return "setItem/upload/complete";
	}
}
