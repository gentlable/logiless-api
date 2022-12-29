package logiless.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("proxy")
public class ProxyProperties {
	
	private String httpHost;
	private String httpPort;
	private String httpsHost;
	private String httpsPort;
	private String id;
	private String password;
	
	public String getHttpHost() {
		return httpHost;
	}
	public void setHttpHost(String httpHost) {
		this.httpHost = httpHost;
	}
	public String getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(String httpPort) {
		this.httpPort = httpPort;
	}
	public String getHttpsHost() {
		return httpsHost;
	}
	public void setHttpsHost(String httpsHost) {
		this.httpsHost = httpsHost;
	}
	public String getHttpsPort() {
		return httpsPort;
	}
	public void setHttpsPort(String httpsPort) {
		this.httpsPort = httpsPort;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
