package logiless;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import logiless.web.com.storage.StorageProperties;
import logiless.web.model.service.ProxyService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
public class LogilessApplication {

	private final ProxyService proxyService;

	@Autowired
	public LogilessApplication(ProxyService proxyService) {
		this.proxyService = proxyService;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(LogilessApplication.class, args);
		LogilessApplication app = ctx.getBean(LogilessApplication.class);

		// プロキシ突破のため一度httpでネット接続する。
		app.proxyService.proxyAccess();

	}

}
