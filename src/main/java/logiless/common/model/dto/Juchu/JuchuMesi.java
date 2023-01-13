package logiless.common.model.dto.Juchu;

import logiless.common.model.dto.Syohn.Article;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JuchuMesi {
	private String id;
	private String code;
	private String status;
	private String articleCode;
	private String articleName;
	private String articleOption;
	private String price;
	private String quantity;
	private String isParent;
	private String isChild;
	private String cachedAllocatedQuantity;
	private String isPartialCancel;
	private String taxIndicator;
	private String taxRate;
	private String taxTotal;
	private String subtotal;
	private String deadline;
	private String lotNumber;
	private String createdAt;
	private String updatedAt;
	private Article article;
	private JuchuOrigin origin;
}
