package logiless.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import logiless.common.model.dto.common.SessionComponent;
import logiless.web.model.service.OAuth2Service;

@RestController
public class LogilessRestController {

	private final SessionComponent sessionComponent;
	private final OAuth2Service oauth2Service;

	@Autowired
	public LogilessRestController(SessionComponent sessionComponent, OAuth2Service oauth2Service) {
		this.sessionComponent = sessionComponent;
		this.oauth2Service = oauth2Service;
	}

	@GetMapping("/logilessGetOrderApi")
	public String logilessGetOrderApi() {

		RestTemplate rest = new RestTemplate();

		final String endpoint = "https://app2.logiless.com/api/v1/merchant/{merchant_id}/sales_orders/search";
		String merchantId = "1022";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<String> res = rest.exchange(endpoint, HttpMethod.POST, null, String.class, merchantId);

		return res.toString();
	}

}
