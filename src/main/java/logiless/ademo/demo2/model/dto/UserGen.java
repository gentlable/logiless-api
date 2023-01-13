package logiless.ademo.demo2.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGen<T> {
	
	private String name;
	
	private Information information;
	
	private List<T> favoriteFood;

}
