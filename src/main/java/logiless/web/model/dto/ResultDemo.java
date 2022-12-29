package logiless.web.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDemo {
	
	private String number;
	private String tenpoCode;
	private String setItemCode;
	private String setItemName;
	
	public ResultDemo() {
		this("1", "1234", "1234567890", "デモセット商品名");
	}
	
	public ResultDemo(String number) {
		this(number, 1000 + Integer.parseInt(number) + "", 1000000000 + Integer.parseInt(number) + "", "デモセット商品名" + number);
	}
	
	public ResultDemo(String number, String tenpoCode, String setItemCode, String setItemName) {
		this.number = number;
		this.tenpoCode = tenpoCode;
		this.setItemCode = setItemCode;
		this.setItemName = setItemName;
	}
}
