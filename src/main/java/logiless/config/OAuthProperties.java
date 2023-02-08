package logiless.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("oauth2.logiless")
@Getter
@Setter
public class OAuth2Properties {

	private String clientId;
	private String clientSecret;
	private String redirectUri;
}
