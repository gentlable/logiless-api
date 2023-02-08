package logiless.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * oauth2.0 認証用プロパティ
 * 
 * @author nsh14789
 *
 */
@Component
@ConfigurationProperties("oauth2.logiless")
@Getter
@Setter
public class OAuthProperties {

	private String clientId;
	private String clientSecret;
	private String redirectUri;
}
