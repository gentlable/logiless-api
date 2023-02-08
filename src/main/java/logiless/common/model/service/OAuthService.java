package logiless.common.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import logiless.common.model.dto.oAuth.OAuth;
import logiless.config.OAuthProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * OAuthに関連する処理
 * 
 * @author nsh14789
 *
 */
@Service
@Slf4j
public class OAuthService {

	private final OAuthProperties oAuth2Properties;
	private final OAuthTokenService oAuthTokenService;

	@Autowired
	public OAuthService(OAuthProperties oauth2Properties, OAuthTokenService oAuthTokenService) {
		this.oAuth2Properties = oauth2Properties;
		this.oAuthTokenService = oAuthTokenService;
	}

	/**
	 * ロジレスの承認画面へのリダイレクト
	 * 
	 * @return
	 */
	public String getUrl() {
		String clientId = oAuth2Properties.getClientId();
		String responseType = "code";
		String redirectUri = oAuth2Properties.getRedirectUri();
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
	public ResponseEntity<OAuth> getOAuth2(String code) {

		RestTemplate rest = new RestTemplate();
		final String endpoint = "https://app2.logiless.com/oauth2/token?client_id={client_id}&client_secret={client_secret}&code={code}&grant_type={grant_type}&redirect_uri={redirect_uri}";
		String clientId = oAuth2Properties.getClientId();
		String clientSecret = oAuth2Properties.getClientSecret();
		String grantType = "authorization_code";
		String redirectUri = oAuth2Properties.getRedirectUri();

		// リクエスト情報の作成
		// TODO ここで取得できなかった時
		try {

			RequestEntity<?> req = RequestEntity.get(endpoint, clientId, clientSecret, code, grantType, redirectUri)
					.build();

			ResponseEntity<OAuth> res = rest.exchange(req, OAuth.class);

			String accessToken = res.getBody().getAccessToken();
			String newRefreshToken = res.getBody().getRefreshToken();

			if (!oAuthTokenService.save("logiless", accessToken, newRefreshToken)) {
				return null;
			}

			return res;

		} catch (Exception e) {
			return null;

		}

	}

	/**
	 * トークンの再取得
	 * 
	 * @return
	 */
	public boolean refreshToken() {
		RestTemplate rest = new RestTemplate();
		final String endpoint = "https://app2.logiless.com/oauth2/token?client_id={client_id}&client_secret={client_secret}&refresh_token={refresh_token}&grant_type={grant_type}";
		String clientId = oAuth2Properties.getClientId();
		String clientSecret = oAuth2Properties.getClientSecret();
		String refreshToken = oAuthTokenService.getOAuthTokenByPlatform("logiless").getRefreshToken();
		String grantType = "refresh_token";

		if (refreshToken == null) {
			log.error("リフレッシュトークンがnullです");

			return false;
		}

		try {
			// リクエスト情報の作成
			RequestEntity<?> req = RequestEntity.get(endpoint, clientId, clientSecret, refreshToken, grantType).build();
			ResponseEntity<OAuth> res = rest.exchange(req, OAuth.class);

			String accessToken = res.getBody().getAccessToken();
			String newRefreshToken = res.getBody().getRefreshToken();

			oAuthTokenService.save("logiless", accessToken, newRefreshToken);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
