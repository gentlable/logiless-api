package logiless.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import logiless.common.model.dto.common.LogilessResponse;
import logiless.common.model.dto.common.SessionComponent;
import logiless.common.model.service.LogilessApiService;
import logiless.web.model.dto.OAuth2;
import logiless.web.model.dto.Tenpo;
import logiless.web.model.service.OAuth2Service;
import lombok.extern.slf4j.Slf4j;

/**
 * 本アプリの基本コントローラー
 * 
 * @author nsh14789
 *
 */
@Controller
@Slf4j
public class LogilessController {

	private static final String merchantId = "1022";

	private final SessionComponent sessionComponent;
	private final MessageSource messageSource;
	private final LogilessApiService logilessApiService;
	private final OAuth2Service oauth2Service;

	@Autowired
	public LogilessController(SessionComponent sessionComponent, MessageSource messageSource,
			LogilessApiService logilessApiService, OAuth2Service oauth2Service) {
		this.sessionComponent = sessionComponent;
		this.messageSource = messageSource;
		this.logilessApiService = logilessApiService;
		this.oauth2Service = oauth2Service;
	}

	/**
	 * ホーム画面へ遷移
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String index(Model model) {
		// リソースからメッセージを取得するサンプル
//		System.out.println(messageSource.getMessage("hello", new String[]{}, Locale.getDefault()));

//		model.addAttribute("code", "1");
		return "index";
	}

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
	public String logilessGetOAuth2(@RequestParam("code") String code, Model model) {

		ResponseEntity<OAuth2> res = oauth2Service.getOAuth2(code);
		model.addAttribute("statusCode", res.getStatusCode());

		sessionComponent.setAccessToken(res.getBody().getAccessToken());
		sessionComponent.setRefreshToken(res.getBody().getRefreshToken());

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

		if (!oauth2Service.refreshToken()) {
			return "logiless/index";
		}

		sessionComponent.setAccessToken(sessionComponent.getAccessToken());
		sessionComponent.setRefreshToken(sessionComponent.getRefreshToken());

		return "logiless/getOAuth2";
	}

	/**
	 * ロジレスに登録されている店舗リスト表示（連携デモ用）
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
		headers.add("Authorization", "Bearer " + sessionComponent.getAccessToken());
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

	@GetMapping("/logiless/salesOrders")
	public String logilessSalesOrders(Model model) {

		return "logiless/salesOrders";
	}

	@GetMapping("/logiless/api/get/salesOrders")
	public String logilessApiGetSalesOrders(Model model, @RequestParam("syoriDt") String syoriDt, boolean... bs) {

		boolean res = logilessApiService.getJuchu(syoriDt, "3901");

		if (!res) {
			model.addAttribute("e", "エラー");
			return "logiless/index";
		}
		model.addAttribute("e", "成功した");
		return "logiless/index";
	}
}
