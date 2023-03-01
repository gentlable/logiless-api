package logiless.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * ファイル出力プロパティ
 * 
 * @author nsh14789
 *
 */
@Component
@ConfigurationProperties("file.output")
@Getter
@Setter
public class FileOutputProperties {

	private String directory;
	private String test;

}
