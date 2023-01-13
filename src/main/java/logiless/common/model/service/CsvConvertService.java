package logiless.common.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import logiless.common.model.dto.Juchu.JuchuCsv;
import logiless.common.model.dto.Juchu.JuchuDenpyo;
import logiless.common.model.dto.Juchu.JuchuMesi;
import logiless.common.model.dto.Sykka.SykkaDenpyo;

@Service
public class CsvConvertService {

	public List<JuchuCsv> juchuCsvConvert(List<JuchuDenpyo> data) {

		List<JuchuCsv> list = new ArrayList<>();

		for (JuchuDenpyo juchuDenpyo : data) {
			for (JuchuMesi line : juchuDenpyo.getLines()) {
				JuchuCsv juchuCsv = new JuchuCsv();
				
				// TODO 一つ目で大丈夫？
				SykkaDenpyo sykkaDenpyo = juchuDenpyo.getOutboundDeliveries().get(0);

				juchuCsv.setJuchuCd(juchuDenpyo.getCode());
				juchuCsv.setOyaJuchuCd(juchuDenpyo.getParentCode());
				juchuCsv.setDenpyoDate(juchuDenpyo.getPostingDate());
				juchuCsv.setTenkiDate(juchuDenpyo.getDocumentDate());
				juchuCsv.setDenpyoStatus(juchuDenpyo.getDocumentStatus());
				juchuCsv.setHaisoStatus(juchuDenpyo.getDeliveryStatus());
				juchuCsv.setNyukinStatus(juchuDenpyo.getIncomingPaymentStatus());
				juchuCsv.setSyoninStatus(juchuDenpyo.getAuthorizationStatus());
				juchuCsv.setKokyakuCd(juchuDenpyo.getCustomerCode());
				juchuCsv.setKonyusyaNm1(juchuDenpyo.getBuyerName1());
				juchuCsv.setKonyusyaNm2(juchuDenpyo.getBuyerName2());
				juchuCsv.setKonyusyaNmKana1(juchuDenpyo.getBuyerNameKana1());
				juchuCsv.setKonyusyaNmKana2(juchuDenpyo.getBuyerNameKana2());
				juchuCsv.setKonyusyaCountry(juchuDenpyo.getBuyerCountry());
				juchuCsv.setKonyusyaPostalCd(juchuDenpyo.getBuyerPostCode());
				juchuCsv.setKonyusyaPrefecture(juchuDenpyo.getBuyerPrefecture());
				juchuCsv.setKonyusyaAddress1(juchuDenpyo.getBuyerAddress1());
				juchuCsv.setKonyusyaAddress2(juchuDenpyo.getBuyerAddress2());
				juchuCsv.setKonyusyaAddress3(juchuDenpyo.getBuyerAddress3());
				juchuCsv.setKonyusyaTelNo(juchuDenpyo.getBuyerPhone());
				juchuCsv.setKonyusyaFaxNo(juchuDenpyo.getBuyerFax());
				juchuCsv.setKonyusyaMailAddress(juchuDenpyo.getBuyerEmail());
				juchuCsv.setTdkskNm1(juchuDenpyo.getRecipientName1());
				juchuCsv.setTdkskNm2(juchuDenpyo.getRecipientName2());
				juchuCsv.setTdkskNmKana1(juchuDenpyo.getRecipientNameKana1());
				juchuCsv.setTdkskNmKana2(juchuDenpyo.getRecipientNameKana2());
				juchuCsv.setTdkskCountry(juchuDenpyo.getRecipientCountry());
				juchuCsv.setTdkskPostalCd(juchuDenpyo.getRecipientPostCode());
				juchuCsv.setTdkskPrefecture(juchuDenpyo.getRecipientPrefecture());
				juchuCsv.setTdkskAddress1(juchuDenpyo.getRecipientAddress1());
				juchuCsv.setTdkskAddress2(juchuDenpyo.getRecipientAddress2());
				juchuCsv.setTdkskAddress3(juchuDenpyo.getRecipientAddress3());
				juchuCsv.setTdkskTelNo(juchuDenpyo.getRecipientPhone());
				juchuCsv.setTdkskFaxNo(juchuDenpyo.getRecipientFax());
				juchuCsv.setTdkskMailAddress(juchuDenpyo.getRecipientEmail());
				juchuCsv.setTotalKingaku(juchuDenpyo.getTotal());
				juchuCsv.setSyohnTotal(juchuDenpyo.getSubtotal());
				juchuCsv.setPointRiyo(juchuDenpyo.getUsePoint());
				juchuCsv.setCouponRiyo(juchuDenpyo.getUseCoupon());
				juchuCsv.setNebiki(juchuDenpyo.getDiscount());
				juchuCsv.setSoryo(juchuDenpyo.getDeliveryFee());
				juchuCsv.setTesuryo(juchuDenpyo.getSundryFee());
				juchuCsv.setZeiTotal(juchuDenpyo.getTaxTotal());
				juchuCsv.setSiharaiHoho(juchuDenpyo.getPaymentMethod());
				juchuCsv.setHaisoHoho(juchuDenpyo.getDeliveryMethod());
				juchuCsv.setHaisoOndo(juchuDenpyo.getDeliveryTemperatureControl());
				juchuCsv.setTdkKiboDate(juchuDenpyo.getDeliveryPreferredDate());
				juchuCsv.setTdkKiboJikantai(juchuDenpyo.getDeliveryPreferredTimeZone());
				juchuCsv.setKonyusyaBiko(juchuDenpyo.getBuyerComment());
				juchuCsv.setMarchantBiko(juchuDenpyo.getMerchantComment());
				juchuCsv.setSykkaSzTokki(juchuDenpyo.getPickingNotes());
				juchuCsv.setNohinsyoTokki(juchuDenpyo.getStatementNotes());
				juchuCsv.setOkurijoTokki(juchuDenpyo.getWayBillNotes());
				juchuCsv.setGiftSitei(juchuDenpyo.getGift());
				juchuCsv.setNoshiSitei(juchuDenpyo.getNoshi());
				juchuCsv.setWrappingSitei(juchuDenpyo.getWrapping());
				juchuCsv.setSykkaTuti(juchuDenpyo.getShippingConfirmed());
				juchuCsv.setJuchuDate(juchuDenpyo.getOrderedAt()); // TODO
				juchuCsv.setNyukinDate("不明"); // TODO
				juchuCsv.setKanryoDate(juchuDenpyo.getFinishedAt()); // TODO
				juchuCsv.setJuchuMesiCd(line.getCode());
				juchuCsv.setJuchuMesiStatus(line.getStatus());
				juchuCsv.setSyohnCd(line.getArticleCode());
				juchuCsv.setSyohnNm(line.getArticleName());
				juchuCsv.setMesiBiko(line.getArticleOption());
				juchuCsv.setHanbaiTnk(line.getPrice());
				juchuCsv.setZeiKb(line.getTaxIndicator());
				juchuCsv.setZeiRitu(line.getTaxRate());
				juchuCsv.setSu(line.getQuantity());
				juchuCsv.setSyokei(line.getSubtotal());
				juchuCsv.setHikiateMatiSu(line.getCachedAllocatedQuantity());
				juchuCsv.setHikiateZumiSu(line.getCachedAllocatedQuantity());
				juchuCsv.setOyaMesi(line.getIsParent());
				juchuCsv.setKoMesi(line.getIsChild());
				juchuCsv.setSoukoId(sykkaDenpyo.getWarehouse()); // TODO
				juchuCsv.setTenpoId(juchuDenpyo.getStore().getCode());
				juchuCsv.setTenpoNm(juchuDenpyo.getStore().getName());
				juchuCsv.setTenpoCd(juchuDenpyo.getStore().getCode()); // TODO
				juchuCsv.setPlatform("汎用");
				juchuCsv.setLogilessCd(juchuDenpyo.getId()); // TODO
				juchuCsv.setFree1(juchuDenpyo.getAttr1());
				juchuCsv.setFree2(juchuDenpyo.getAttr2());
				juchuCsv.setFree3(juchuDenpyo.getAttr3());
				juchuCsv.setFree4(juchuDenpyo.getAttr4());
				juchuCsv.setFree5(juchuDenpyo.getAttr5());
				juchuCsv.setFree6(juchuDenpyo.getAttr6());
				juchuCsv.setFree7(juchuDenpyo.getAttr7());
				juchuCsv.setFree8(juchuDenpyo.getAttr8());
				juchuCsv.setFree9(juchuDenpyo.getAttr9());
				juchuCsv.setFree10(juchuDenpyo.getAttr10());
				juchuCsv.setSykkaDenHaisoTuisekiNoList(
						sykkaDenpyo.getDeliveryTrackingNumbers().get(0));
				juchuCsv.setSykkaDenHaisoHoho(sykkaDenpyo.getDeliveryMethod());
				juchuCsv.setSykkaDenHaisoStatus(sykkaDenpyo.getDeliveryStatus());
				juchuCsv.setSykkaDenDenpyoStatus(sykkaDenpyo.getDocumentStatus());
				juchuCsv.setSykkaDenTdkKiboDate(sykkaDenpyo.getDeliveryPreferredDate());
				juchuCsv.setSykkaDenTdkKiboJikantai(sykkaDenpyo.getDeliveryPreferredTimeZone());
				juchuCsv.setTnk("");
				juchuCsv.setOyaSyohnCd("");
				list.add(juchuCsv);
			}

		}

		return list;
	}

}
