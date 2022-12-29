package logiless;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import logiless.web.com.storage.StorageProperties;
import logiless.web.model.service.ProxyService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
//@EnableScheduling
public class LogilessApplication {
	
	@Autowired
	ProxyService ps;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(LogilessApplication.class, args);
		LogilessApplication app = ctx.getBean(LogilessApplication.class);
		
		// プロキシ突破のため一度httpでネット接続する。
		app.ps.proxyAccess();

	}

}
