package logiless.web.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import logiless.common.model.dto.common.LogilessResponse;
import logiless.common.model.dto.oAuth.OAuth;
import logiless.common.model.service.LogilessApiService;
import logiless.common.model.service.OAuthService;
import logiless.common.model.service.OAuthTokenService;
import logiless.web.model.dto.Tenpo;
import logiless.web.model.service.SetItemService;
import lombok.extern.slf4j.Slf4j;

/**
 * 本アプリの基本コントローラー
 * 
 * @author nsh14789
 *
 */
@Controller
@Slf4j
@SessionAttributes(value = { "tenpoList" })
public class LogilessController {

	private static final String merchantId = "1022";

	private final MessageSource messageSource;
	private final LogilessApiService logilessApiService;
	private final OAuthService oauth2Service;
	private final OAuthTokenService oAuthTokenService;
	private final SetItemService setItemService;

	@Autowired
	public LogilessController(MessageSource messageSource, LogilessApiService logilessApiService,
			OAuthService oauth2Service, OAuthTokenService oAuthTokenService, SetItemService setItemService) {
		this.messageSource = messageSource;
		this.logilessApiService = logilessApiService;
		this.oauth2Service = oauth2Service;
		this.oAuthTokenService = oAuthTokenService;
		this.setItemService = setItemService;
	}

	@ModelAttribute(value = "tenpoList")
	public List<Tenpo> createTenpoList() {
		return setItemService.getAllTenpoList();
	}

	/**
	 * ホーム画面へ遷移
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String index(Model model) {
		// リソースからメッセージを取得するサンプル TODO
//		System.out.println(messageSource.getMessage("hello", new String[]{}, Locale.getDefault()));
		return "index";
	}

	// TODO
	@GetMapping("error")
	public String error(Model model) {
		model.addAttribute("test", "test");
		return "error";
	}

	/**
	 * ロジレス連携画面遷移
	 * 
	 * @return
	 */
	@GetMapping("/logiless")
	public String logiless() {
		return "logiless/index";
	}

	/**
	 * Oauth2.0 承認画面遷移（リダイレクト）
	 * 
	 * @return
	 */
	@GetMapping("/logiless/get")
	public String logilessGet() {
		String redirectUrl = oauth2Service.getUrl();
		return "redirect:" + redirectUrl;
	}

	/**
	 * リダイレクトで承認結果を表示する画面
	 * 
	 * @param code
	 * @param model
	 * @return
	 */
	@GetMapping("/logiless/getOAuth2")
	public String logilessGetOAuth2(@RequestParam(name = "code", required = false) String code, Model model) {

		ResponseEntity<OAuth> res = oauth2Service.getOAuth2(code);

		if (res != null) {
			model.addAttribute("message", "アクセストークンの取得に成功しました。");
		} else {
			model.addAttribute("message", "アクセストークンの取得に失敗しました。");
		}

		return "logiless/getOAuth2";
	}

	/**
	 * マニュアルリフレッシュ結果画面遷移
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/logiless/refresh")
	public String logilessRefresh(Model model) {

		if (oauth2Service.refreshToken()) {
			model.addAttribute("message", "アクセストークンの取得に成功しました。");
		} else {
			model.addAttribute("message", "アクセストークンの取得に失敗しました。");
		}

		return "logiless/getOAuth2";
	}

	/**
	 * ロジレスに登録されている店舗を連携する
	 * 
	 * @param model
	 * @param bs
	 * @return
	 */
	@GetMapping("/logiless/get/tenpos/api")
	public String logilessGetTenposApi(Model model, boolean... bs) {

		RestTemplate rest = new RestTemplate();

		final String endpoint = "https://app2.logiless.com/api/v1/merchant/{merchant_id}/stores";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Bearer " + oAuthTokenService.getOAuthTokenByPlatform("logiless").getAccessToken());
		try {
			// リクエスト情報の作成
			RequestEntity<?> req = RequestEntity.get(endpoint, merchantId).headers(headers).build();

			ResponseEntity<String> res = rest.exchange(req, String.class);
			String json = res.getBody();

			ObjectMapper mapper = new ObjectMapper();

			LogilessResponse<Tenpo> response = mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
					.readValue(json, new TypeReference<LogilessResponse<Tenpo>>() {
					});

			model.addAttribute("tenpoList", response.getData());

			return "master/tenpoList";
		} catch (HttpClientErrorException e) {
			e.printStackTrace();

			if (!oauth2Service.refreshToken()) {
				System.out.println("トークンリフレッシュに失敗しました。");
				return "";
			}

			if (!bs[0]) {
				return logilessGetTenposApi(model, true);
			} else {
				System.out.println("システムエラー");
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();

			return "";
		}
	}

	/**
	 * 出荷実績を取得する画面へ遷移
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/logiless/salesOrders")
	public String logilessSalesOrders(Model model) {

		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		model.addAttribute("date", date.format(formatter));

		return "logiless/salesOrders";
	}

	/**
	 * 出荷実績を取得する処理
	 * 
	 * @param model
	 * @param syoriDt
	 * @return
	 */
	@GetMapping("/logiless/api/get/salesOrders")
	public String logilessApiGetSalesOrders(@RequestParam("syoriDt") String syoriDt,
			@RequestParam("tenpo") String tenpo, Model model) {

		boolean res = logilessApiService.getJuchu(syoriDt, "3901");

		if (!res) {
			// TODO エラーハンドリング
			model.addAttribute("message", "エラー");
			return "/logiless/api/get/salesOrders";
		}
		model.addAttribute("message", "出荷実績の取得に成功しました");
		return "logiless/complete";
	}

	/**
	 * 出荷実績を取得する処理<br>
	 * 試験の為一気に取得する用。<br>
	 * TODO リリース時削除
	 * 
	 * @param model
	 * @param syoriDt
	 * @return
	 */
	@GetMapping("/logiless/api/get/salesOrders/test")
	public String logilessApiGetSalesOrdersTest(@RequestParam("syoriDtFr") String syoriDtFr,
			@RequestParam("syoriDtTo") String syoriDtTo, RedirectAttributes redirectAttributes) {

		DateTimeFormatter dateformatterYYYYMMDD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatterMMDD = DateTimeFormatter.ofPattern("MMdd");
		boolean res = false;

		// 開始日と終了日を日付型にする。
		LocalDate syoriDtFrDate = LocalDate.parse(syoriDtFr, dateformatterYYYYMMDD);
		LocalDate syoriDtToDate = LocalDate.parse(syoriDtTo, dateformatterYYYYMMDD);

		LocalDate syoriDtDate = syoriDtFrDate;

		// ループする
		while (true) {
			res = logilessApiService.getJuchu(syoriDtDate, "3901", "FFAASSJ1.D" + syoriDtDate.format(formatterMMDD));
			syoriDtDate = syoriDtDate.plusDays(1);

			if (syoriDtDate.isAfter(syoriDtToDate)) {
				break;
			}
		}

		if (!res) {
			redirectAttributes.addAttribute("e", "エラー");
			return "redirect:/logiless";
		}
		redirectAttributes.addAttribute("e", "成功した");
		return "redirect:/logiless";
	}

}
