package logiless.common.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import logiless.common.model.dto.juchu.JuchuCsv;
import logiless.common.model.dto.juchu.JuchuDenpyo;
import logiless.common.model.dto.juchu.JuchuMesi;
import logiless.common.model.dto.sykka.SykkaDenpyo;
import logiless.web.model.dto.BaraItem;
import logiless.web.model.service.SetItemService;

@Service
public class JuchuCsvConvertService {

	private final SetItemService setItemService;

	@Autowired
	public JuchuCsvConvertService(SetItemService setItemService) {
		this.setItemService = setItemService;
	}

	private static final Map<String, String> DENPYO_STATUS_MAP = new HashMap<String, String>() {
		{
			put("Processing", "処理中");
			put("WaitingForPayment", "入金待ち");
			put("WaitingForAllocation", "引当待ち");
			put("WaitingForShipment", "出荷待ち");
			put("Shipped", "出荷済み");
			put("Cancel", "キャンセル");
		}
	};

	private static final Map<String, String> HIKIATE_STATUS_MAP = new HashMap<String, String>() {
		{
			put("WaitingForAllocation", "引当待ち");
			put("Allocated", "引当済み");
		}
	};

	private static final Map<String, String> HAISO_STATUS_MAP = new HashMap<String, String>() {
		{
			put("WaitingForShipment", "出荷待ち");
			put("Working", "出荷作業中");
			put("PartlyShipped", "一部出荷済み");
			put("Shipped", "出荷済み");
			put("Pending	", "保留");
			put("Cancel", "キャンセル");
		}
	};

	private static final Map<String, String> NYUKIN_STATUS_MAP = new HashMap<String, String>() {
		{
			put("NotPaid", "未入金");
			put("PartlyPaid", "一部入金済み");
			put("Paid", "入金済み");
		}
	};

	private static final Map<String, String> SYONIN_STATUS_MAP = new HashMap<String, String>() {
		{
			put("NotRequired", "必要なし");
			put("Unauthorized", "未承認");
			put("Authorizing", "承認中");
			put("Authorized", "承認済み");
			put("Captured", "請求済み");
			put("AuthorizationFailure", "承認NG");
		}
	};

	private static final Map<String, String> MESI_STATUS_MAP = new HashMap<String, String>() {
		{
			put("WaitingForTransfer", "入荷待ち");
			put("WaitingForAllocation", "引当待ち");
			put("Allocated", "引当済み");
			put("Shipped", "出荷済み");
			put("Cancel", "キャンセル");
		}
	};

	private static final Map<String, String> HAISO_HOHO_MAP = new HashMap<String, String>() {
		{
			put("yamato", "ヤマト運輸 宅急便");
			put("yamato_compact", "ヤマト運輸 宅急便コンパクト");
			put("yamato_nekoposu", "ヤマト運輸 ネコポス");
			put("yamato_mail", "ヤマト運輸 DM便");
			put("yamato_poa", "ヤマト運輸 宅急便（着払い）");
			put("sagawa", "佐川急便 飛脚宅配便");
			put("sagawa_mail", "佐川急便 飛脚メール便");
			put("sagawa_yu_mail", "佐川急便 飛脚ゆうメール便");
			put("you_pack", "日本郵便 ゆうパック");
			put("you_pack_poa", "日本郵便 ゆうパック（着払い）");
			put("yu_packet", "日本郵便 ゆうパケット");
			put("yu_packet_1cm", "日本郵便 ゆうパケット（厚さ1cm以内）");
			put("yu_packet_2cm", "日本郵便 ゆうパケット（厚さ2cm以内）");
			put("yu_packet_3cm", "日本郵便 ゆうパケット（厚さ3cm以内）");
			put("yu_mail", "日本郵便 ゆうメール");
			put("yu_mail_150g", "日本郵便 ゆうメール（重さ150g）");
			put("yu_mail_250g", "日本郵便 ゆうメール（重さ250g）");
			put("yu_mail_500g", "日本郵便 ゆうメール（重さ500g）");
			put("yu_mail_1000g", "日本郵便 ゆうメール（重さ1kg）");
			put("letter_pack_plus", "日本郵便 レターパック プラス");
			put("letter_pack_light", "日本郵便 レターパック ライト");
			put("standard_size_mail", "日本郵便 定形郵便");
			put("non_standard_size_mail", "日本郵便 定形外郵便");
			put("click_post", "日本郵便 クリックポスト");
			put("ems", "日本郵便 国際スピード郵便（EMS）");
			put("international_e_packet", "日本郵便 国際eパケット");
			put("acceptance_recorded_mail", "日本郵便 特定記録郵便");
			put("small_packing", "日本郵便 小形包装物");
			put("seino_express", "西濃運輸 カンガルー特急便");
			put("seino_mini", "西濃運輸 カンガルーミニ便");
			put("ecohai", "エコ配 宅配便（全国版）");
			put("dhl", "DHL エクスプレスサービス");
			put("fed_ex", "FedEx 国際宅配便");
			put("ups", "UPS 国際輸送サービス");
			put("ecms", "ECMS B2Cダイレクト");
			put("pegasus", "Pegasus 国際宅配便");
			put("global_e_parcel", "Global eParcel Global eParcel Solutions");
			put("rakuten_express", "Rakuten EXPRESS 宅配便");
			put("rakuten_express_mail", "Rakuten EXPRESS メール便");
			put("yamato_rakuten_cvs_receipt", "ヤマト運輸 楽天市場 コンビニ受取");
			put("japan_post_rakuten_cvs_receipt", "日本郵便 楽天市場 コンビニ受取");
			put("japan_post_rakuten_po_receipt", "日本郵便 楽天市場 郵便局受取");
			put("sagawa_rakuten_cvs_receipt", "佐川急便 楽天市場 コンビニ受取");
			put("yamato_box_charter", "ヤマトボックスチャーター JITBOXチャーター便");
			put("jad", "JAD 宅配急便");
			put("sbs_sokuhai", "SBS即配サポート EC宅配便");
			put("nittsu", "日本通運 宅配便");
			put("nittsu_express_high_speed", "日本通運 エクスプレスハイスピード");
			put("fukutsu", "福山通運 宅配便");
			put("tonami", "トナミ運輸 宅配便");
			put("kinbutsu_rex", "近物レックス");
			put("toll_express", "トールエクスプレス 国内便");
			put("kurume", "久留米運送 宅配便");
			put("sanyo_jidosha", "山陽自動車運送 積み合せ便");
			put("last_one_mile_personal_delivery", "ラストワンマイル協同組合 個人配送");
			put("last_one_mile_business_delivery", "ラストワンマイル協同組合 企業配送");
			put("delivery_provider", "（その他） デリバリープロバイダ");
			put("in_store_pickup", "（その他） 店頭受取");
			put("in_warehouse_pickup", "（その他） 倉庫受取");
			put("manufacturer_direct", "（その他） メーカー直送");
			put("charter", "（その他） チャーター便");
		}
	};

	private static final Map<String, String> SIHARAI_HOHO_MAP = new HashMap<String, String>() {
		{
			put("credit_card_payment", "クレジットカード決済");
			put("cod", "代金引換");
			put("bank_transfer", "銀行振込");
			put("cvs_payment", "コンビニ決済");
			put("post_payment", "後払い決済");
			put("cash_payment", "現金払い");
			put("pay_easy", "ペイジー");
			put("paypal", "Paypal");
			put("no_payment", "支払なし");
			put("carrier_billing", "キャリア決済");
			put("electronic_money_payment", "電子マネー決済");
			put("postal_transfer", "郵便振替");
			put("registered_mail", "現金書留");
			put("sales_on_credit", "掛売");
			put("platform_payment", "プラットフォーム決済");
			put("amazon_pay", "Amazon Pay");
			put("amazon_payment", "Amazonペイメント");
			put("rakuten_pay", "楽天ペイ");
			put("rakuten_bank_payment", "楽天バンク決済");
			put("rakuten_edy_payment", "楽天Edy決済");
			put("rakuten_id_payment", "楽天ID決済");
			put("yahoo_wallet", "Yahoo!ウォレット");
			put("yahoo_carrier_billing", "Yahoo!キャリア決済");
			put("line_pay", "LINE Pay");
			put("np_atobarai", "NP後払い");
			put("nissen_collect_post_payment", "スコア @払い決済");
			put("d_barai", "Yahoo!NTTドコモ d払い");
			put("dsk_cvs_payment", "電算システム コンビニ決済");
			put("web_money", "WebMoney");
			put("paypay_balance_payment", "PayPay 残高払い");
		}
	};

	private static final Map<String, String> OISOGI_KB_MAP = new HashMap<String, String>() {
		{
			put("SameDayDelivery", "当日便");
			put("NextDayDelivery", "翌日便");
			put("SecondDayDelivery", "翌々日便");
		}
	};

	private static final Map<String, String> HAISO_ONDO_MAP = new HashMap<String, String>() {
		{
			put("Normal", "通常");
			put("Cold", "冷蔵");
			put("Frozen", "冷凍");
		}
	};

	private static final Map<String, String> ZEI_KB_MAP = new HashMap<String, String>() {
		{
			put("TaxExcluded", "税抜");
			put("TaxIncluded", "税込");
			put("Exempt", "非課税");
		}
	};

	private static final Map<String, String> ZEI_KEISAN_JUNJO_MAP = new HashMap<String, String>() {
		{
			put("UnitByUnit", "商品単位");
			put("LineByLine", "明細単位");
			put("OrderByOrder", "受注単位");
		}
	};

	private static final Map<String, String> ZEI_MARUME_MAP = new HashMap<String, String>() {
		{
			put("RoundOff", "四捨五入");
			put("RoundDown", "切り捨て");
			put("RoundUp", "切り上げ");
		}
	};

	private static final Map<String, String> SYOHN_KB_MAP = new HashMap<String, String>() {
		{
			put("Single", "通常商品");
			put("Assortment", "セット商品");
			put("Crate", "集合包装");
		}
	};

	private static final Map<String, String> NYUKA_YOTEI_MAP = new HashMap<String, String>() {
		{
			put("WaitingForReceipt", "入荷待ち");
			put("Received", "入荷済み");
			put("Cancel", "キャンセル");
		}
	};

	private static final Map<String, String> ZAIKO_SOSA_KB_MAP = new HashMap<String, String>() {
		{
			put("Transfer", "入荷予定");
			put("TransferReversal", "入荷予定取消");
			put("Receipt", "入荷");
			put("ReceiptReversal", "入荷取消");
			put("PutAway", "入庫");
			put("Allocation", "引当");
			put("Moving", "移動");
			put("Issue", "出庫");
			put("Shipment", "出荷");
			put("ShipmentReversal", "出荷取消");
			put("PhysicalInventory", "実地棚卸");
			put("ReturnDelivery", "返品入荷");
			put("Conversion", "変換");
		}
	};

	private static final Map<String, String> ZAIKO_LAYER_MAP = new HashMap<String, String>() {
		{
			put("Article", "商品");
			put("Warehouse", "倉庫");
			put("Location", "ロケーション");
			put("Deadline", "出荷期限日");
			put("LotNumber", "ロット番号");
		}
	};

	/**
	 * JuchuDenpyoリストをJuchuCsvリストに変換する
	 * 
	 * @param data
	 * @return
	 */
	public List<JuchuCsv> juchuCsvConvert(List<JuchuDenpyo> data) {

		List<JuchuCsv> list = new ArrayList<>();

		for (JuchuDenpyo juchuDenpyo : data) {
			for (JuchuMesi line : juchuDenpyo.getLines()) {

				if (!"Shipped".equals(line.getStatus())) {
					continue;
				}

				JuchuCsv juchuCsv = new JuchuCsv();

				SykkaDenpyo sykkaDenpyo = juchuDenpyo.getOutboundDeliveries().get(0);

				juchuCsv.setJuchuCd(StringUtils.defaultString(juchuDenpyo.getCode()));
				juchuCsv.setOyaJuchuCd(StringUtils.defaultString(juchuDenpyo.getParentCode()));
				juchuCsv.setDenpyoDate(StringUtils.defaultString(juchuDenpyo.getPostingDate()));
				juchuCsv.setTenkiDate(StringUtils.defaultString(juchuDenpyo.getDocumentDate()));
				juchuCsv.setDenpyoStatus(
						DENPYO_STATUS_MAP.get(StringUtils.defaultString(juchuDenpyo.getDocumentStatus())));
				juchuCsv.setHaisoStatus(
						HAISO_STATUS_MAP.get(StringUtils.defaultString(juchuDenpyo.getDeliveryStatus())));
				juchuCsv.setNyukinStatus(
						NYUKIN_STATUS_MAP.get(StringUtils.defaultString(juchuDenpyo.getIncomingPaymentStatus())));
				juchuCsv.setSyoninStatus(
						SYONIN_STATUS_MAP.get(StringUtils.defaultString(juchuDenpyo.getAuthorizationStatus())));
				juchuCsv.setKokyakuCd(StringUtils.defaultString(juchuDenpyo.getCustomerCode()));
				juchuCsv.setKonyusyaNm1(StringUtils.defaultString(juchuDenpyo.getBuyerName1()));
				juchuCsv.setKonyusyaNm2(StringUtils.defaultString(juchuDenpyo.getBuyerName2()));
				juchuCsv.setKonyusyaNmKana1(StringUtils.defaultString(juchuDenpyo.getBuyerNameKana1()));
				juchuCsv.setKonyusyaNmKana2(StringUtils.defaultString(juchuDenpyo.getBuyerNameKana2()));
				juchuCsv.setKonyusyaCountry(StringUtils.defaultString(juchuDenpyo.getBuyerCountry()));
				juchuCsv.setKonyusyaPostalCd(StringUtils.defaultString(juchuDenpyo.getBuyerPostCode()));
				juchuCsv.setKonyusyaPrefecture(StringUtils.defaultString(juchuDenpyo.getBuyerPrefecture()));
				juchuCsv.setKonyusyaAddress1(StringUtils.defaultString(juchuDenpyo.getBuyerAddress1()));
				juchuCsv.setKonyusyaAddress2(StringUtils.defaultString(juchuDenpyo.getBuyerAddress2()));
				juchuCsv.setKonyusyaAddress3(StringUtils.defaultString(juchuDenpyo.getBuyerAddress3()));
				juchuCsv.setKonyusyaTelNo(StringUtils.defaultString(juchuDenpyo.getBuyerPhone()));
				juchuCsv.setKonyusyaFaxNo(StringUtils.defaultString(juchuDenpyo.getBuyerFax()));
				juchuCsv.setKonyusyaMailAddress(StringUtils.defaultString(juchuDenpyo.getBuyerEmail()));
				juchuCsv.setTdkskNm1(StringUtils.defaultString(juchuDenpyo.getRecipientName1()));
				juchuCsv.setTdkskNm2(StringUtils.defaultString(juchuDenpyo.getRecipientName2()));
				juchuCsv.setTdkskNmKana1(StringUtils.defaultString(juchuDenpyo.getRecipientNameKana1()));
				juchuCsv.setTdkskNmKana2(StringUtils.defaultString(juchuDenpyo.getRecipientNameKana2()));
				juchuCsv.setTdkskCountry(StringUtils.defaultString(juchuDenpyo.getRecipientCountry()));
				juchuCsv.setTdkskPostalCd(StringUtils.defaultString(juchuDenpyo.getRecipientPostCode()));
				juchuCsv.setTdkskPrefecture(StringUtils.defaultString(juchuDenpyo.getRecipientPrefecture()));
				juchuCsv.setTdkskAddress1(StringUtils.defaultString(juchuDenpyo.getRecipientAddress1()));
				juchuCsv.setTdkskAddress2(StringUtils.defaultString(juchuDenpyo.getRecipientAddress2()));
				juchuCsv.setTdkskAddress3(StringUtils.defaultString(juchuDenpyo.getRecipientAddress3()));
				juchuCsv.setTdkskTelNo(StringUtils.defaultString(juchuDenpyo.getRecipientPhone()));
				juchuCsv.setTdkskFaxNo(StringUtils.defaultString(juchuDenpyo.getRecipientFax()));
				juchuCsv.setTdkskMailAddress(StringUtils.defaultString(juchuDenpyo.getRecipientEmail()));
				juchuCsv.setTotalKingaku(StringUtils.defaultString(juchuDenpyo.getTotal()));
				juchuCsv.setSyohnTotal(StringUtils.defaultString(juchuDenpyo.getSubtotal()));
				juchuCsv.setPointRiyo(StringUtils.defaultString(juchuDenpyo.getUsePoint()));
				juchuCsv.setCouponRiyo(StringUtils.defaultString(juchuDenpyo.getUseCoupon()));
				juchuCsv.setNebiki(StringUtils.defaultString(juchuDenpyo.getDiscount()));
				juchuCsv.setSoryo(StringUtils.defaultString(juchuDenpyo.getDeliveryFee()));
				juchuCsv.setTesuryo(StringUtils.defaultString(juchuDenpyo.getSundryFee()));
				juchuCsv.setZeiTotal(StringUtils.defaultString(juchuDenpyo.getTaxTotal()));
				juchuCsv.setSiharaiHoho(
						SIHARAI_HOHO_MAP.get(StringUtils.defaultString(juchuDenpyo.getPaymentMethod())));
				juchuCsv.setHaisoHoho(HAISO_HOHO_MAP.get(StringUtils.defaultString(juchuDenpyo.getDeliveryMethod())));
				juchuCsv.setHaisoOndo(
						HAISO_ONDO_MAP.get(StringUtils.defaultString(juchuDenpyo.getDeliveryTemperatureControl())));
				juchuCsv.setTdkKiboDate(StringUtils.defaultString(juchuDenpyo.getDeliveryPreferredDate()));
				juchuCsv.setTdkKiboJikantai(StringUtils.defaultString(juchuDenpyo.getDeliveryPreferredTimeZone()));
				juchuCsv.setKonyusyaBiko(StringUtils.defaultString(juchuDenpyo.getBuyerComment()));
				juchuCsv.setMarchantBiko(StringUtils.defaultString(juchuDenpyo.getMerchantComment()));
				juchuCsv.setSykkaSzTokki(StringUtils.defaultString(juchuDenpyo.getPickingNotes()));
				juchuCsv.setNohinsyoTokki(StringUtils.defaultString(juchuDenpyo.getStatementNotes()));
				juchuCsv.setOkurijoTokki(StringUtils.defaultString(juchuDenpyo.getWayBillNotes()));
				juchuCsv.setGiftSitei(StringUtils.defaultString(juchuDenpyo.getGift()));
				juchuCsv.setNoshiSitei(StringUtils.defaultString(juchuDenpyo.getNoshi()));
				juchuCsv.setWrappingSitei(StringUtils.defaultString(juchuDenpyo.getWrapping()));
				juchuCsv.setSykkaTuti(StringUtils.defaultString(juchuDenpyo.getShippingConfirmed()));
				juchuCsv.setJuchuDate(StringUtils.defaultString(juchuDenpyo.getOrderedAt()));
				juchuCsv.setNyukinDate(""); // TODO （使わないようなので空でいいかも）
				juchuCsv.setKanryoDate(StringUtils.defaultString(juchuDenpyo.getFinishedAt()));
				juchuCsv.setJuchuMesiCd(StringUtils.defaultString(line.getCode()));
				juchuCsv.setJuchuMesiStatus(MESI_STATUS_MAP.get(StringUtils.defaultString(line.getStatus())));
				juchuCsv.setSyohnCd(StringUtils.defaultString(line.getArticleCode()));
				juchuCsv.setSyohnNm(StringUtils.defaultString(line.getArticleName()));
				juchuCsv.setMesiBiko(StringUtils.defaultString(line.getArticleOption()));
				juchuCsv.setHanbaiTnk(StringUtils.defaultString(line.getPrice()));
				juchuCsv.setZeiKb(ZEI_KB_MAP.get(StringUtils.defaultString(line.getTaxIndicator())));
				juchuCsv.setZeiRitu(StringUtils.defaultString(line.getTaxRate()));
				juchuCsv.setSu(StringUtils.defaultString(line.getQuantity()));
				juchuCsv.setSyokei(StringUtils.defaultString(line.getSubtotal()));
				if ("0".equals(StringUtils.defaultString(line.getIsParent()))) {
					juchuCsv.setHikiateMatiSu("0");
					juchuCsv.setHikiateZumiSu(StringUtils.defaultString(line.getCachedAllocatedQuantity()));
				} else {
					juchuCsv.setHikiateMatiSu(StringUtils.defaultString(line.getQuantity()));
					juchuCsv.setHikiateZumiSu("0");
				}
				juchuCsv.setOyaMesi(StringUtils.defaultString(line.getIsParent()));
				juchuCsv.setKoMesi(StringUtils.defaultString(line.getIsChild()));
				juchuCsv.setSoukoId(StringUtils.defaultString(sykkaDenpyo.getWarehouse()));
				juchuCsv.setTenpoId(StringUtils.defaultString(juchuDenpyo.getStore().getCode()));
				juchuCsv.setTenpoNm(StringUtils.defaultString(juchuDenpyo.getStore().getName()));
				juchuCsv.setTenpoCd("");// TODO(利用していなさそうなので) 今後のこと考えるのであれば、店舗コードとプラットフォームは必要、でも二重管理になるのめちゃいやだな
				juchuCsv.setPlatform("汎用"); // TODO(オールアバウトだけなので汎用) 今後のこと考えるのであれば、店舗コードとプラットフォームは必要、でも二重管理になるのめちゃいやだな
				juchuCsv.setLogilessCd(StringUtils.defaultString(line.getArticle().getObjectCode()));
				juchuCsv.setFree1(StringUtils.defaultString(juchuDenpyo.getAttr1()));
				juchuCsv.setFree2(StringUtils.defaultString(juchuDenpyo.getAttr2()));
				juchuCsv.setFree3(StringUtils.defaultString(juchuDenpyo.getAttr3()));
				juchuCsv.setFree4(StringUtils.defaultString(juchuDenpyo.getAttr4()));
				juchuCsv.setFree5(StringUtils.defaultString(juchuDenpyo.getAttr5()));
				juchuCsv.setFree6(StringUtils.defaultString(juchuDenpyo.getAttr6()));
				juchuCsv.setFree7(StringUtils.defaultString(juchuDenpyo.getAttr7()));
				juchuCsv.setFree8(StringUtils.defaultString(juchuDenpyo.getAttr8()));
				juchuCsv.setFree9(StringUtils.defaultString(juchuDenpyo.getAttr9()));
				juchuCsv.setFree10(StringUtils.defaultString(juchuDenpyo.getAttr10()));
				juchuCsv.setSykkaDenHaisoTuisekiNoList(
						StringUtils.defaultString(sykkaDenpyo.getDeliveryTrackingNumbers().get(0)));
				juchuCsv.setSykkaDenHaisoHoho(
						HAISO_HOHO_MAP.get(StringUtils.defaultString(sykkaDenpyo.getDeliveryMethod())));
				juchuCsv.setSykkaDenHaisoStatus(
						HAISO_STATUS_MAP.get(StringUtils.defaultString(sykkaDenpyo.getDeliveryStatus())));
				juchuCsv.setSykkaDenDenpyoStatus(
						DENPYO_STATUS_MAP.get(StringUtils.defaultString(sykkaDenpyo.getDocumentStatus())));
				juchuCsv.setSykkaDenTdkKiboDate(StringUtils.defaultString(sykkaDenpyo.getDeliveryPreferredDate()));
				juchuCsv.setSykkaDenTdkKiboJikantai(
						StringUtils.defaultString(sykkaDenpyo.getDeliveryPreferredTimeZone()));
				juchuCsv.setTnk(StringUtils.defaultString(line.getTnk()));
				juchuCsv.setOyaSyohnCd(StringUtils.defaultString(line.getOyaSyohnCd()));

				// TODO 本来はjacksonのconfigでできそうだができなかったので一旦残してある。
//				juchuCsv.setJuchuCd(juchuDenpyo.getCode());
//				juchuCsv.setOyaJuchuCd(juchuDenpyo.getParentCode());
//				juchuCsv.setDenpyoDate(juchuDenpyo.getPostingDate());
//				juchuCsv.setTenkiDate(juchuDenpyo.getDocumentDate());
//				juchuCsv.setDenpyoStatus(DENPYO_STATUS_MAP.get(juchuDenpyo.getDocumentStatus()));
//				juchuCsv.setHaisoStatus(HAISO_STATUS_MAP.get(juchuDenpyo.getDeliveryStatus()));
//				juchuCsv.setNyukinStatus(NYUKIN_STATUS_MAP.get(juchuDenpyo.getIncomingPaymentStatus()));
//				juchuCsv.setSyoninStatus(SYONIN_STATUS_MAP.get(juchuDenpyo.getAuthorizationStatus()));
//				juchuCsv.setKokyakuCd(juchuDenpyo.getCustomerCode());
//				juchuCsv.setKonyusyaNm1(juchuDenpyo.getBuyerName1());
//				juchuCsv.setKonyusyaNm2(juchuDenpyo.getBuyerName2());
//				juchuCsv.setKonyusyaNmKana1(juchuDenpyo.getBuyerNameKana1());
//				juchuCsv.setKonyusyaNmKana2(juchuDenpyo.getBuyerNameKana2());
//				juchuCsv.setKonyusyaCountry(juchuDenpyo.getBuyerCountry());
//				juchuCsv.setKonyusyaPostalCd(juchuDenpyo.getBuyerPostCode());
//				juchuCsv.setKonyusyaPrefecture(juchuDenpyo.getBuyerPrefecture());
//				juchuCsv.setKonyusyaAddress1(juchuDenpyo.getBuyerAddress1());
//				juchuCsv.setKonyusyaAddress2(juchuDenpyo.getBuyerAddress2());
//				juchuCsv.setKonyusyaAddress3(juchuDenpyo.getBuyerAddress3());
//				juchuCsv.setKonyusyaTelNo(juchuDenpyo.getBuyerPhone());
//				juchuCsv.setKonyusyaFaxNo(juchuDenpyo.getBuyerFax());
//				juchuCsv.setKonyusyaMailAddress(juchuDenpyo.getBuyerEmail());
//				juchuCsv.setTdkskNm1(juchuDenpyo.getRecipientName1());
//				juchuCsv.setTdkskNm2(juchuDenpyo.getRecipientName2());
//				juchuCsv.setTdkskNmKana1(juchuDenpyo.getRecipientNameKana1());
//				juchuCsv.setTdkskNmKana2(juchuDenpyo.getRecipientNameKana2());
//				juchuCsv.setTdkskCountry(juchuDenpyo.getRecipientCountry());
//				juchuCsv.setTdkskPostalCd(juchuDenpyo.getRecipientPostCode());
//				juchuCsv.setTdkskPrefecture(juchuDenpyo.getRecipientPrefecture());
//				juchuCsv.setTdkskAddress1(juchuDenpyo.getRecipientAddress1());
//				juchuCsv.setTdkskAddress2(juchuDenpyo.getRecipientAddress2());
//				juchuCsv.setTdkskAddress3(juchuDenpyo.getRecipientAddress3());
//				juchuCsv.setTdkskTelNo(juchuDenpyo.getRecipientPhone());
//				juchuCsv.setTdkskFaxNo(juchuDenpyo.getRecipientFax());
//				juchuCsv.setTdkskMailAddress(juchuDenpyo.getRecipientEmail());
//				juchuCsv.setTotalKingaku(juchuDenpyo.getTotal());
//				juchuCsv.setSyohnTotal(juchuDenpyo.getSubtotal());
//				juchuCsv.setPointRiyo(juchuDenpyo.getUsePoint());
//				juchuCsv.setCouponRiyo(juchuDenpyo.getUseCoupon());
//				juchuCsv.setNebiki(juchuDenpyo.getDiscount());
//				juchuCsv.setSoryo(juchuDenpyo.getDeliveryFee());
//				juchuCsv.setTesuryo(juchuDenpyo.getSundryFee());
//				juchuCsv.setZeiTotal(juchuDenpyo.getTaxTotal());
//				juchuCsv.setSiharaiHoho(SIHARAI_HOHO_MAP.get(juchuDenpyo.getPaymentMethod()));
//				juchuCsv.setHaisoHoho(HAISO_HOHO_MAP.get(juchuDenpyo.getDeliveryMethod()));
//				juchuCsv.setHaisoOndo(HAISO_ONDO_MAP.get(juchuDenpyo.getDeliveryTemperatureControl()));
//				juchuCsv.setTdkKiboDate(juchuDenpyo.getDeliveryPreferredDate());
//				juchuCsv.setTdkKiboJikantai(juchuDenpyo.getDeliveryPreferredTimeZone());
//				juchuCsv.setKonyusyaBiko(juchuDenpyo.getBuyerComment());
//				juchuCsv.setMarchantBiko(juchuDenpyo.getMerchantComment());
//				juchuCsv.setSykkaSzTokki(juchuDenpyo.getPickingNotes());
//				juchuCsv.setNohinsyoTokki(juchuDenpyo.getStatementNotes());
//				juchuCsv.setOkurijoTokki(juchuDenpyo.getWayBillNotes());
//				juchuCsv.setGiftSitei(juchuDenpyo.getGift());
//				juchuCsv.setNoshiSitei(juchuDenpyo.getNoshi());
//				juchuCsv.setWrappingSitei(juchuDenpyo.getWrapping());
//				juchuCsv.setSykkaTuti(juchuDenpyo.getShippingConfirmed());
//				juchuCsv.setJuchuDate(juchuDenpyo.getOrderedAt());
//				juchuCsv.setNyukinDate(""); // TODO （使わないようなので空でいいかも）
//				juchuCsv.setKanryoDate(juchuDenpyo.getFinishedAt());
//				juchuCsv.setJuchuMesiCd(line.getCode());
//				juchuCsv.setJuchuMesiStatus(MESI_STATUS_MAP.get(line.getStatus()));
//				juchuCsv.setSyohnCd(line.getArticleCode());
//				juchuCsv.setSyohnNm(line.getArticleName());
//				juchuCsv.setMesiBiko(line.getArticleOption());
//				juchuCsv.setHanbaiTnk(line.getPrice());
//				juchuCsv.setZeiKb(ZEI_KB_MAP.get(line.getTaxIndicator()));
//				juchuCsv.setZeiRitu(line.getTaxRate());
//				juchuCsv.setSu(line.getQuantity());
//				juchuCsv.setSyokei(line.getSubtotal());
//				if ("0".equals(line.getIsParent())) {
//					juchuCsv.setHikiateMatiSu("0");
//					juchuCsv.setHikiateZumiSu(line.getCachedAllocatedQuantity());
//				} else {
//					juchuCsv.setHikiateMatiSu(line.getQuantity());
//					juchuCsv.setHikiateZumiSu("0");
//				}
//				juchuCsv.setOyaMesi(line.getIsParent());
//				juchuCsv.setKoMesi(line.getIsChild());
//				juchuCsv.setSoukoId(sykkaDenpyo.getWarehouse());
//				juchuCsv.setTenpoId(juchuDenpyo.getStore().getCode());
//				juchuCsv.setTenpoNm(juchuDenpyo.getStore().getName());
//				juchuCsv.setTenpoCd("");// TODO(利用していなさそうなので) 今後のこと考えるのであれば、店舗コードとプラットフォームは必要、でも二重管理になるのめちゃいやだな
//				juchuCsv.setPlatform("汎用"); // TODO(オールアバウトだけなので汎用) 今後のこと考えるのであれば、店舗コードとプラットフォームは必要、でも二重管理になるのめちゃいやだな
//				juchuCsv.setLogilessCd(line.getArticle().getObjectCode());
//				juchuCsv.setFree1(juchuDenpyo.getAttr1());
//				juchuCsv.setFree2(juchuDenpyo.getAttr2());
//				juchuCsv.setFree3(juchuDenpyo.getAttr3());
//				juchuCsv.setFree4(juchuDenpyo.getAttr4());
//				juchuCsv.setFree5(juchuDenpyo.getAttr5());
//				juchuCsv.setFree6(juchuDenpyo.getAttr6());
//				juchuCsv.setFree7(juchuDenpyo.getAttr7());
//				juchuCsv.setFree8(juchuDenpyo.getAttr8());
//				juchuCsv.setFree9(juchuDenpyo.getAttr9());
//				juchuCsv.setFree10(juchuDenpyo.getAttr10());
//				juchuCsv.setSykkaDenHaisoTuisekiNoList(sykkaDenpyo.getDeliveryTrackingNumbers().get(0));
//				juchuCsv.setSykkaDenHaisoHoho(HAISO_HOHO_MAP.get(sykkaDenpyo.getDeliveryMethod()));
//				juchuCsv.setSykkaDenHaisoStatus(HAISO_STATUS_MAP.get(sykkaDenpyo.getDeliveryStatus()));
//				juchuCsv.setSykkaDenDenpyoStatus(DENPYO_STATUS_MAP.get(sykkaDenpyo.getDocumentStatus()));
//				juchuCsv.setSykkaDenTdkKiboDate(sykkaDenpyo.getDeliveryPreferredDate());
//				juchuCsv.setSykkaDenTdkKiboJikantai(sykkaDenpyo.getDeliveryPreferredTimeZone());
//				juchuCsv.setTnk(line.getTnk());
//				juchuCsv.setOyaSyohnCd(line.getOyaSyohnCd());

				list.add(juchuCsv);
			}

		}

		return list;
	}

	/**
	 * 受注伝票データのバラ商品について単価を付与<br>
	 * 親明細、セット商品マスタの明細、子明細の順
	 * 
	 * @param juchuDenpyo
	 * @return
	 */
	public JuchuDenpyo addBaraItem(JuchuDenpyo juchuDenpyo) {

		List<JuchuMesi> juchuMesiList = juchuDenpyo.getLines();
		List<JuchuMesi> newJuchuMesiList = new ArrayList<>();

		for (int i = 0; i < juchuMesiList.size(); i++) {

			JuchuMesi juchuMesi = juchuMesiList.get(i);
			newJuchuMesiList.add(juchuMesi);

			if (!"1".equals(juchuMesi.getIsParent())) {
				continue;
			}

			String tenpoCd = juchuDenpyo.getStore().getCode();
			String oyaSyohnCd = juchuMesi.getArticleCode();

			List<BaraItem> baraItemList = setItemService.getBaraItemByTenpoCodeAndSetItemCode(tenpoCd, oyaSyohnCd);

			for (BaraItem baraItem : baraItemList) {

				boolean existFlg = false;

				for (JuchuMesi rec : juchuMesiList) {

					if (rec.getArticleCode().equals(baraItem.getCode())
							&& rec.getQuantity().equals(rec.getQuantity())) {

						JuchuMesi newRec = new JuchuMesi();

						BeanUtils.copyProperties(rec, newRec);

						newRec.setIsChild("9");
						newRec.setTnk(String.format("%.2f", baraItem.getPrice()));
						newRec.setOyaSyohnCd(baraItem.getSetItemCode());

						newJuchuMesiList.add(newRec);

						existFlg = true;

						break;
					}
				}

				if (!existFlg) {
					JuchuMesi newRec = new JuchuMesi();
					BeanUtils.copyProperties(juchuMesi, newRec);

					newRec.setArticleCode(baraItem.getCode());
					newRec.setArticleName(baraItem.getName());
					newRec.setQuantity(baraItem.getQuantity() + "");
					newRec.setIsParent("0");
					newRec.setIsChild("9");
					newRec.setTnk(String.format("%.2f", baraItem.getPrice()));
					newRec.setOyaSyohnCd(baraItem.getSetItemCode());

					newJuchuMesiList.add(newRec);
				}

			}

		}
		juchuDenpyo.setLines(newJuchuMesiList);

		return juchuDenpyo;
	}

	/**
	 * 受注伝票データのバラ商品について単価を付与<br>
	 * 親明細の次にマスタからのデータを追加<br>
	 * 最後に子明細をまとめて追加
	 * 
	 * @param juchuDenpyo
	 * @return
	 */
	public JuchuDenpyo addBaraItem1(JuchuDenpyo juchuDenpyo) {

		List<JuchuMesi> juchuMesiList = juchuDenpyo.getLines();
		// 新しい明細一覧作成
		List<JuchuMesi> newJuchuMesiList = new ArrayList<>();
		// 子明細避難用一覧作成
		List<JuchuMesi> tempJuchuChildMesiList = new ArrayList<>();

		for (int i = 0; i < juchuMesiList.size(); i++) {

			JuchuMesi juchuMesi = juchuMesiList.get(i);

			// セット商品じゃない明細は新しい明細一覧にそのまま追加して処理を行わない
			if ("0".equals(juchuMesi.getIsParent()) && "0".equals(juchuMesi.getIsChild())) {
				newJuchuMesiList.add(juchuMesi);
				continue;
			}

			// 子明細は最後に追加するため一旦別リストに保存
			if ("1".equals(juchuMesi.getIsChild())) {
				tempJuchuChildMesiList.add(juchuMesi);
				continue;
			}

			// セット商品じゃないもの、親明細を新しい明細一覧に追加
			newJuchuMesiList.add(juchuMesi);

			String tenpoCd = juchuDenpyo.getStore().getCode();
			String oyaSyohnCd = juchuMesi.getArticleCode();

			List<BaraItem> baraItemList = setItemService.getBaraItemByTenpoCodeAndSetItemCode(tenpoCd, oyaSyohnCd);

			for (BaraItem baraItem : baraItemList) {

				boolean existFlg = false;

				for (JuchuMesi rec : juchuMesiList) {

					if (rec.getArticleCode().equals(baraItem.getCode())
							&& rec.getQuantity().equals(rec.getQuantity())) {

						JuchuMesi newRec = new JuchuMesi();

						BeanUtils.copyProperties(rec, newRec);

						newRec.setIsChild("9");
						newRec.setTnk(String.format("%.2f", baraItem.getPrice()));
						newRec.setOyaSyohnCd(baraItem.getSetItemCode());

						newJuchuMesiList.add(newRec);

						existFlg = true;

						break;
					}
				}

				if (!existFlg) {
					JuchuMesi newRec = new JuchuMesi();
					BeanUtils.copyProperties(juchuMesi, newRec);

					newRec.setArticleCode(baraItem.getCode());
					newRec.setArticleName(baraItem.getName());
					newRec.setQuantity(baraItem.getQuantity() + "");
					newRec.setIsParent("0");
					newRec.setIsChild("9");
					newRec.setTnk(String.format("%.2f", baraItem.getPrice()));
					newRec.setOyaSyohnCd(baraItem.getSetItemCode());

					newJuchuMesiList.add(newRec);
				}

			}

		}
		newJuchuMesiList.addAll(tempJuchuChildMesiList);
		juchuDenpyo.setLines(newJuchuMesiList);

		return juchuDenpyo;
	}

	// 最後に追加情報追加
	public JuchuDenpyo addBaraItem2(JuchuDenpyo juchuDenpyo) {

		List<JuchuMesi> juchuMesiList = juchuDenpyo.getLines();
		List<JuchuMesi> newJuchuMesiList = new ArrayList<>();

		for (JuchuMesi juchuMesi : juchuMesiList) {

			if (!juchuMesi.getIsParent().equals("1")) {
				continue;
			}

			String tenpoCd = juchuDenpyo.getStore().getCode();
			String oyaSyohnCd = juchuMesi.getArticleCode();

			List<BaraItem> baraItemList = setItemService.getBaraItemByTenpoCodeAndSetItemCode(tenpoCd, oyaSyohnCd);

			for (BaraItem baraItem : baraItemList) {

				boolean existFlg = false;

				for (JuchuMesi rec : juchuMesiList) {

					if (rec.getArticleCode().equals(baraItem.getCode())
							&& rec.getQuantity().equals(rec.getQuantity())) {

						JuchuMesi newRec = new JuchuMesi();

						BeanUtils.copyProperties(rec, newRec);

						newRec.setIsChild("9");
						newRec.setTnk(String.format("%.2f", baraItem.getPrice()));
						newRec.setOyaSyohnCd(baraItem.getSetItemCode());

						newJuchuMesiList.add(newRec);

						existFlg = true;

						break;
					}
				}

				if (!existFlg) {
					JuchuMesi newRec = new JuchuMesi();
					BeanUtils.copyProperties(juchuMesi, newRec);

					newRec.setArticleCode(baraItem.getCode());
					newRec.setArticleName(baraItem.getName());
					newRec.setQuantity(baraItem.getQuantity() + "");
					newRec.setIsParent("0");
					newRec.setIsChild("9");
					newRec.setTnk(String.format("%.2f", baraItem.getPrice()));
					newRec.setOyaSyohnCd(baraItem.getSetItemCode());

					newJuchuMesiList.add(newRec);
				}
			}
		}
		juchuMesiList.addAll(newJuchuMesiList);
		juchuDenpyo.setLines(juchuMesiList);

		return juchuDenpyo;
	}

}
