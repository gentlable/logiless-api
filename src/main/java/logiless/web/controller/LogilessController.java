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
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import logiless.common.model.dto.Juchu.JuchuCsv;
import logiless.common.model.service.CsvConvertService;
import logiless.common.model.service.FileOutputService;
import logiless.web.config.SessionSample;
import logiless.web.model.dto.LogilessResponseJuchu;
import logiless.web.model.dto.LogilessResponseTenpo;
import logiless.web.model.dto.OAuth2;
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
	@Autowired
	private CsvConvertService csvConvertService;
	@Autowired
	private FileOutputService fileOutputService;

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
	public String logilessGetOAuth2(@RequestParam("code") String code, Model model) {

		ResponseEntity<OAuth2> res = oauth2Service.getOAuth2(code);
		model.addAttribute("statusCode", res.getStatusCode());

		sessionSample.setAccessToken(res.getBody().getAccessToken());
		sessionSample.setRefreshToken(res.getBody().getRefreshToken());

		return "logiless/getOAuth2";
	}

	@GetMapping("/logiless/refresh")
	public String logilessRefresh(Model model) {

		if (!oauth2Service.refreshToken()) {
			return "logiless/index";
		}

		sessionSample.setAccessToken(sessionSample.getAccessToken());
		sessionSample.setRefreshToken(sessionSample.getRefreshToken());

		return "logiless/getOAuth2";
	}

	@GetMapping("/logiless/get/tenpos/api")
	public String logilessGetTenposApi(Model model, boolean... bs) {

		RestTemplate rest = new RestTemplate();

		final String endpoint = "https://app2.logiless.com/api/v1/merchant/{merchant_id}/stores";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + sessionSample.getAccessToken());
		try {
			// リクエスト情報の作成
			RequestEntity<?> req = RequestEntity.get(endpoint, merchantId).headers(headers).build();

			ResponseEntity<String> res = rest.exchange(req, String.class);
			String json = res.getBody();

			ObjectMapper mapper = new ObjectMapper();

			// JSON⇒Javaオブジェクトに変換
			LogilessResponseTenpo data = mapper.readValue(json, LogilessResponseTenpo.class);

			model.addAttribute("tenpoList", data.getData());

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

	@GetMapping("/logiless/api/get/salesOrders")
	public String logilessApiGetSalesOrders(Model model, boolean... bs) {

		RestTemplate rest = new RestTemplate();

		String endpoint = "https://app2.logiless.com/api/v1/merchant/{merchant_id}/sales_orders?limit=1&delivery_status=Shipped";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + sessionSample.getAccessToken());
		try {
			// リクエスト情報の作成
			RequestEntity<?> req = RequestEntity.get(endpoint, merchantId).headers(headers).build();

			ResponseEntity<String> res = rest.exchange(req, String.class);
			String json = res.getBody();

			ObjectMapper objectMapper = new ObjectMapper();

			// JSON⇒Javaオブジェクトに変換
			LogilessResponseJuchu response = objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
					.readValue(json, LogilessResponseJuchu.class);

			CsvMapper csvMapper = new CsvMapper();
			CsvSchema schema = csvMapper.schemaFor(JuchuCsv.class).withHeader();
			fileOutputService.output("demo",
					csvMapper.writer(schema).writeValueAsString(csvConvertService.juchuCsvConvert(response.getData())));

			model.addAttribute("e", "成功した");
			model.addAttribute("data", response.getData());

			return "logiless/index";
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
			model.addAttribute("e", e);
			return "logiless/index";
		}
	}
}
