package logiless.common.model.dto.juchu;

import logiless.common.model.dto.syohn.Article;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

	// CSV出力用
	private String tnk;
	private String oyaSyohnCd;

}
