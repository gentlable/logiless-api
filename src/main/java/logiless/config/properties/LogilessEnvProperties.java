package logiless.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 環境情報を設定予定
 * 
 * @author nsh14789
 *
 */
@Component
@ConfigurationProperties("env")
public class LogilessEnvProperties {

}
