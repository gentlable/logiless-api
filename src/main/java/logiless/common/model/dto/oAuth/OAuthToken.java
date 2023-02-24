package logiless.common.model.dto.oAuth;

import lombok.Getter;
import lombok.Setter;

/**
 * OAuthトークンエンティティ
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
public class OAuthToken {

	private String platform;

	private String accessToken;

	private String refreshToken;

}
