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

import com.fasterxml.jackson.databind.ObjectMapper;

import logiless.web.config.SessionSample;
import logiless.web.model.dto.LogilessResponse;
import logiless.web.model.dto.OAuth2;
import logiless.web.model.dto.Tenpo;
import logiless.web.model.service.OAuth2Service;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LogilessController {

	private static final String merchantId = "1022";

	@Autowired
	protected SessionSample sessionSample;
	@SuppressWarnings("unused")
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private OAuth2Service oauth2Service;

	@GetMapping("/")
	public String index() {
		// リソースからメッセージを取得する参考
//		System.out.println(messageSource.getMessage("hello", new String[]{}, Locale.getDefault()));

		// Spring Boot では、デフォルトのログレベルにINFOが設定されているため、TRACEとDEBUGは出力されません。
		return "index";
	}

	@GetMapping("/logiless")
	public String logiless() {
		return "logiless/index";
	}
	
	@GetMapping("/logiless/get")
	public String logilessGet() {
		String redirectUrl = oauth2Service.getUrl();
		return "redirect:" + redirectUrl;
	}

	@GetMapping("/logiless/getOAuth2")
	public String logilessGetOAuth2(@RequestParam("code") String code,
			Model model) {

		ResponseEntity<OAuth2> res = oauth2Service.getOAuth2(code);
		model.addAttribute("status_code", res.getStatusCode());
		model.addAttribute("access_token", res.getBody().getAccess_token());
		model.addAttribute("refresh_token", res.getBody().getRefresh_token());

		sessionSample.setAccess_token(res.getBody().getAccess_token());
		sessionSample.setRefresh_token(res.getBody().getRefresh_token());

		return "logiless/getOAuth2";
	}
	
	@GetMapping("/logiless/get/tenpos/api")
	public String logilessGetTenposApi(Model model, boolean... bs) {

		RestTemplate rest = new RestTemplate();

		final String endpoint = "https://app2.logiless.com/api/v1/merchant/{merchant_id}/stores";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + sessionSample.getAccess_token());
		try {
			// リクエスト情報の作成
			RequestEntity<?> req = RequestEntity.get(endpoint, merchantId).headers(headers).build();
			
			ResponseEntity<String> res = rest.exchange(req, String.class);
			String json = res.getBody();
			
			ObjectMapper mapper = new ObjectMapper();

			//JSON⇒Javaオブジェクトに変換
			LogilessResponse<Tenpo> data = mapper.readValue(json, LogilessResponse.class);
			
			model.addAttribute("tenpoList", data.getData());

			return "master/tenpoList";
		} catch (HttpClientErrorException e){
			e.printStackTrace();
			
			if(!oauth2Service.refreshToken()) {
				System.out.println("トークンリフレッシュに失敗しました。");
				return "";
			}
			
			if(!bs[0]) {
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
	
	
	@GetMapping("/logiless/api/get/salesOrders")
	public String logilessApiGetSalesOrders(Model model, boolean... bs) {

		RestTemplate rest = new RestTemplate();

		final String endpoint = "https://app2.logiless.com/api/v1/merchant/{merchant_id}/sales_orders";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + sessionSample.getAccess_token());
		try {
			// リクエスト情報の作成
			RequestEntity<?> req = RequestEntity.get(endpoint, merchantId).headers(headers).build();
			
			ResponseEntity<String> res = rest.exchange(req, String.class);
			String json = res.getBody();
			
			ObjectMapper mapper = new ObjectMapper();

			//JSON⇒Javaオブジェクトに変換
			LogilessResponse<Tenpo> data = mapper.readValue(json, LogilessResponse.class);
			
			model.addAttribute("tenpoList", data.getData());

			return "master/tenpoList";
		} catch (HttpClientErrorException e){
			e.printStackTrace();
			
			if(!oauth2Service.refreshToken()) {
				System.out.println("トークンリフレッシュに失敗しました。");
				return "";
			}
			
			if(!bs[0]) {
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
}
