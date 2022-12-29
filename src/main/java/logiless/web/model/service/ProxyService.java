package logiless.web.model.service;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import logiless.web.config.ProxyProperties;

@Service
public class ProxyService {

	private ProxyProperties proxyProperties;

	@Autowired
	ProxyService(ProxyProperties proxyProperties) {
		this.proxyProperties = proxyProperties;
	}

	public void proxyAccess() {
		System.setProperty("http.proxyHost", proxyProperties.getHttpHost());
		System.setProperty("http.proxyPort", proxyProperties.getHttpPort());
		System.setProperty("https.proxyHost", proxyProperties.getHttpsHost());
		System.setProperty("https.proxyPort", proxyProperties.getHttpsPort());
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(proxyProperties.getId(), proxyProperties.getPassword().toCharArray());
			}
		});
		RestTemplate rest = new RestTemplate();

		// TODO マジで意味わからん
		final String endpoint = "http://start.spring.io/";

		rest.getForEntity(endpoint, String.class);
	}
}
