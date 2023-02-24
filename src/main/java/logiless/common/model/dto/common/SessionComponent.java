package logiless.common.model.dto.common;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * セッション情報を扱う TODO これでいいのか
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionComponent implements Serializable {

	private static final long serialVersionUID = -8419277060363935532L;

	private String username = "廣海 真吾";

}
