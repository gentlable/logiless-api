package logiless.common.model.dto.juchu;

import java.util.List;

import logiless.common.model.dto.sykka.SykkaDenpyo;
import logiless.web.model.dto.Tenpo;
import lombok.Getter;
import lombok.Setter;

/**
 * ロジレス受注伝票
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
public class JuchuDenpyo {

	private String id;
	private String code;
	private String parentCode;
	private String documentStatus;
	private String allocationStatus;
	private String deliveryStatus;
	private String incomingPaymentStatus;
	private String authorizationStatus;
	private String customerCode;
	private String buyerName1;
	private String buyerName2;
	private String buyerNameKana1;
	private String buyerNameKana2;
	private String buyerCountry;
	private String buyerPostCode;
	private String buyerPrefecture;
	private String buyerAddress1;
	private String buyerAddress2;
	private String buyerAddress3;
	private String buyerPhone;
	private String buyerFax;
	private String buyerEmail;
	private String recipientName1;
	private String recipientName2;
	private String recipientNameKana1;
	private String recipientNameKana2;
	private String recipientCountry;
	private String recipientPostCode;
	private String recipientPrefecture;
	private String recipientAddress1;
	private String recipientAddress2;
	private String recipientAddress3;
	private String recipientPhone;
	private String recipientFax;
	private String recipientEmail;
	private String subtotal;
	private String usePoint;
	private String useCoupon;
	private String deliveryFee;
	private String sundryFee;
	private String discount;
	private String taxProcessingMethod;
	private String taxRoundingMethod;
	private String documentTaxRate;
	private String taxTotal;
	private String total;
	private String paymentMethod;
	private String deliveryMethod;
	private String deliveryPackageQuantity;
	private String expressType;
	private String deliveryTemperatureControl;
	private String scheduledShippingDate;
	private String deliveryPreferredDate;
	private String deliveryPreferredTimeZone;
	private String isIsolatedArea;
	private String deliveryTrackingNumber;
	private String buyerComment;
	private String merchantComment;
	private String statementNotes;
	private String pickingNotes;
	private String wayBillNotes;
	private String gift;
	private String wrapping;
	private String noshi;
	private String attr1;
	private String attr2;
	private String attr3;
	private String attr4;
	private String attr5;
	private String attr6;
	private String attr7;
	private String attr8;
	private String attr9;
	private String attr10;
	private String waitingForConfirmation;
	private String shippingConfirmed;
	private String isMultipleDeliveries;
	private String subscriptionId;
	private String subscriptionDeliveryNumber;
	private String subscriptionNextDeliveryDate;
	private String totalQuantity;
	private String purchasingTimes;
	private List<String> tags;
	private String orderedAt;
	private String finishedAt;
	private String createdAt;
	private String updatedAt;
	private List<JuchuMesi> lines;
	private List<SykkaDenpyo> outboundDeliveries;
	private Tenpo store;
	private String warehouse;
	private String codTotal;
	private String postingDate;
	private String documentDate;
}
