package logiless.web.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import logiless.common.model.dto.common.SessionComponent;
import logiless.config.OAuth2Properties;
import logiless.web.model.dto.OAuth2;
import lombok.extern.slf4j.Slf4j;

/**
 * OAuthに関連する処理
 * 
 * @author nsh14789
 *
 */
@Service
@Slf4j
public class OAuth2Service {

	private final SessionComponent sessionComponent;
	private final OAuth2Properties oauth2Properties;

	@Autowired
	public OAuth2Service(SessionComponent sessionComponent, OAuth2Properties oauth2Properties) {
		this.sessionComponent = sessionComponent;
		this.oauth2Properties = oauth2Properties;
	}

	/**
	 * ロジレスの承認画面へのリダイレクト
	 * 
	 * @return
	 */
	public String getUrl() {
		String clientId = oauth2Properties.getClientId();
		String responseType = "code";
		String redirectUri = oauth2Properties.getRedirectUri();
		final String endpoint = "https://app2.logiless.com/oauth/v2/auth?client_id=" + clientId + "&response_type="
				+ responseType + "&redirect_uri=" + redirectUri;

		return endpoint;
	}

	/**
	 * トークンの取得
	 * 
	 * @param code
	 * @return
	 */
	public ResponseEntity<OAuth2> getOAuth2(String code) {

		RestTemplate rest = new RestTemplate();
		final String endpoint = "https://app2.logiless.com/oauth2/token?client_id={client_id}&client_secret={client_secret}&code={code}&grant_type={grant_type}&redirect_uri={redirect_uri}";
		String clientId = oauth2Properties.getClientId();
		String clientSecret = oauth2Properties.getClientSecret();
		String grantType = "authorization_code";
		String redirectUri = oauth2Properties.getRedirectUri();

		// リクエスト情報の作成
		RequestEntity<?> req = RequestEntity.get(endpoint, clientId, clientSecret, code, grantType, redirectUri)
				.build();

		return rest.exchange(req, OAuth2.class);
	}

	/**
	 * トークンの再取得
	 * 
	 * @return
	 */
	public boolean refreshToken() {

		RestTemplate rest = new RestTemplate();
		final String endpoint = "https://app2.logiless.com/oauth2/token?client_id={client_id}&client_secret={client_secret}&refresh_token={refresh_token}&grant_type={grant_type}";
		String clientId = oauth2Properties.getClientId();
		String clientSecret = oauth2Properties.getClientSecret();
		String refreshToken = sessionComponent.getRefreshToken();
		String grantType = "refresh_token";

		if (refreshToken == null) {
			log.error("リフレッシュトークンがnullです");

			return false;
		}

		try {
			// リクエスト情報の作成
			RequestEntity<?> req = RequestEntity.get(endpoint, clientId, clientSecret, refreshToken, grantType).build();
			ResponseEntity<OAuth2> res = rest.exchange(req, OAuth2.class);

			sessionComponent.setAccessToken(res.getBody().getAccessToken());
			sessionComponent.setRefreshToken(res.getBody().getRefreshToken());

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
