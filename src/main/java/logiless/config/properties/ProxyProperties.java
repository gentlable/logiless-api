package logiless.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * プロキシサーバのプロパティ
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@Component
@ConfigurationProperties("proxy")
public class ProxyProperties {

	private String httpHost;
	private String httpPort;
	private String httpsHost;
	private String httpsPort;
	private String id;
	private String password;
}
