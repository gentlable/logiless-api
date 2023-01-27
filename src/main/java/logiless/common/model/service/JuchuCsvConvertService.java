package logiless.common.model.service;

import java.util.ArrayList;
import java.util.List;

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
				juchuCsv.setJuchuDate(juchuDenpyo.getOrderedAt());
				juchuCsv.setNyukinDate(""); // TODO （使わないようなので空でいいかも）
				juchuCsv.setKanryoDate(juchuDenpyo.getFinishedAt());
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
				if ("0".equals(line.getIsParent())) {
					juchuCsv.setHikiateMatiSu("0");
					juchuCsv.setHikiateZumiSu(line.getCachedAllocatedQuantity());
				} else {
					juchuCsv.setHikiateMatiSu(line.getQuantity());
					juchuCsv.setHikiateZumiSu("0");
				}
				juchuCsv.setOyaMesi(line.getIsParent());
				juchuCsv.setKoMesi(line.getIsChild());
				juchuCsv.setSoukoId(sykkaDenpyo.getWarehouse());
				juchuCsv.setTenpoId(juchuDenpyo.getStore().getCode());
				juchuCsv.setTenpoNm(juchuDenpyo.getStore().getName());
				juchuCsv.setTenpoCd("");// TODO(利用していなさそうなので) 今後のこと考えるのであれば、店舗コードとプラットフォームは必要、でも二重管理になるのめちゃいやだな
				juchuCsv.setPlatform("汎用"); // TODO(オールアバウトだけなので汎用) 今後のこと考えるのであれば、店舗コードとプラットフォームは必要、でも二重管理になるのめちゃいやだな
				juchuCsv.setLogilessCd(line.getArticle().getObjectCode());
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
				juchuCsv.setSykkaDenHaisoTuisekiNoList(sykkaDenpyo.getDeliveryTrackingNumbers().get(0));
				juchuCsv.setSykkaDenHaisoHoho(sykkaDenpyo.getDeliveryMethod());
				juchuCsv.setSykkaDenHaisoStatus(sykkaDenpyo.getDeliveryStatus());
				juchuCsv.setSykkaDenDenpyoStatus(sykkaDenpyo.getDocumentStatus());
				juchuCsv.setSykkaDenTdkKiboDate(sykkaDenpyo.getDeliveryPreferredDate());
				juchuCsv.setSykkaDenTdkKiboJikantai(sykkaDenpyo.getDeliveryPreferredTimeZone());
				juchuCsv.setTnk(line.getTnk());
				juchuCsv.setOyaSyohnCd(line.getOyaSyohnCd());

				list.add(juchuCsv);
			}

		}

		return list;
	}

	@Autowired
	SetItemService setItemService;

	/**
	 * 受注伝票データのバラ商品について単価を付与
	 * 
	 * @param juchuDenpyo
	 * @return
	 */
	public JuchuDenpyo AddBaraItem(JuchuDenpyo juchuDenpyo) {

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
						newRec.setTnk(baraItem.getPrice() + "");
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
					newRec.setTnk(baraItem.getPrice() + "");
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
