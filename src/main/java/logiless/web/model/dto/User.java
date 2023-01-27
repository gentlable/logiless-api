package logiless.web.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private String id;
	private String name;
	private String password;
	private String roles;

}
