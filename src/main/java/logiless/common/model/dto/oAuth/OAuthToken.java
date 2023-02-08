package logiless.common.model.dto.oAuth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthToken {

	private long id;

	private String platform;

	private String accessToken;

	private String refreshToken;

}
