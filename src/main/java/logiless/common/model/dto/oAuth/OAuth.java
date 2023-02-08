package logiless.common.model.dto.oAuth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuth {

	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("tokenType")
	private String token_type;
	@JsonProperty("expiresIn")
	private int expires_in;
	private String scope;
	@JsonProperty("refresh_token")
	private String refreshToken;
}
