package logiless.common.model.dto.sykka;

import java.util.List;

import logiless.common.model.dto.juchu.JuchuDenpyo;
import logiless.common.model.dto.juchu.JuchuMesi;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SykkaDenpyo {
	
	private String id;
	private String code;
	private String documentStatus;
	private String allocationStatus;
	private String deliveryStatus;
	private String shipperName1;
	private String shipperName2;
	private String shipperNameKana1;
	private String shipperNameKana2;
	private String shipperCountry;
	private String shipperPostCode;
	private String shipperPrefecture;
	private String shipperAddress1;
	private String shipperAddress2;
	private String shipperAddress3;
	private String shipperPhone;
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
	private String codTotal;
	private String deliveryMethod;
	private String deliveryPackageQuantity;
	private String deliveryTemperatureControl;
	private String scheduledShippingDate;
	private String deliveryPreferredDate;
	private String deliveryPreferredTimeZone;
	private List<String> deliveryTrackingNumbers;
	private String pickingNotes;
	private String wayBillNotes;
	private String gift;
	private String wrapping;
	private String attr1;
	private String attr2;
	private String attr3;
	private String noshi;
	private String totalQuantity;
	private String totalWeight;
	private String totalSizeCoefficient;
	private String waitingForConfirmation;
	private List<JuchuMesi> lines;
	private List<ShippedAcutalStock> shippedActualStocks;
	private String warehouse;
	private String store;
	private JuchuDenpyo salesOrder;
	private String documentDate;
	private String postingDate;
	private String orderedAt;
	private String finishedAt;
	private String createdAt;
	private String updatedAt;
}
