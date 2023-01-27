package logiless.common.model.dto.juchu;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JuchuCsv {
	
	@JsonProperty(value="受注コード", index=0)
	private String juchuCd;

	@JsonProperty(value="親受注コード", index=1)
	private String oyaJuchuCd;
	
	@JsonProperty(value="伝票日付", index=2)
	private String denpyoDate;

	@JsonProperty(value="転記日付", index=3)
	private String tenkiDate;

	@JsonProperty(value="伝票ステータス", index=4)
	private String denpyoStatus;

	@JsonProperty(value="配送ステータス", index=5)
	private String haisoStatus;
	
	@JsonProperty(value="入金ステータス", index=6)
	private String nyukinStatus;
	
	@JsonProperty(value="承認ステータス", index=7)
	private String syoninStatus;
	
	@JsonProperty(value="顧客コード", index=8)
	private String kokyakuCd;
	
	@JsonProperty(value="購入者名1", index=9)
	private String konyusyaNm1;
	
	@JsonProperty(value="購入者名2", index=10)
	private String konyusyaNm2;
	
	@JsonProperty(value="購入者名かな1", index=11)
	private String konyusyaNmKana1;
	
	@JsonProperty(value="購入者名かな2", index=12)
	private String konyusyaNmKana2;
	
	@JsonProperty(value="購入者 国", index=13)
	private String konyusyaCountry;
	
	@JsonProperty(value="購入者 郵便番号", index=14)
	private String konyusyaPostalCd;
	
	@JsonProperty(value="購入者 都道府県", index=15)
	private String konyusyaPrefecture;
	
	@JsonProperty(value="購入者 住所1", index=16)
	private String konyusyaAddress1;
	
	@JsonProperty(value="購入者 住所2", index=17)
	private String konyusyaAddress2;
	
	@JsonProperty(value="購入者 住所3", index=18)
	private String konyusyaAddress3;
	
	@JsonProperty(value="購入者 電話番号", index=19)
	private String konyusyaTelNo;
	
	@JsonProperty(value="購入者 FAX番号", index=20)
	private String konyusyaFaxNo;
	
	@JsonProperty(value="購入者 メールアドレス", index=21)
	private String konyusyaMailAddress;
	
	@JsonProperty(value="お届け先名1", index=22)
	private String tdkskNm1;
	
	@JsonProperty(value="お届け先名2", index=23)
	private String tdkskNm2;
	
	@JsonProperty(value="お届け先名かな1", index=24)
	private String tdkskNmKana1;
	
	@JsonProperty(value="お届け先名かな2", index=25)
	private String tdkskNmKana2;
	
	@JsonProperty(value="お届け先 国", index=26)
	private String tdkskCountry;
	
	@JsonProperty(value="お届け先 郵便番号", index=27)
	private String tdkskPostalCd;
	
	@JsonProperty(value="お届け先 都道府県", index=28)
	private String tdkskPrefecture;
	
	@JsonProperty(value="お届け先 住所1", index=29)
	private String tdkskAddress1;
	
	@JsonProperty(value="お届け先 住所2", index=30)
	private String tdkskAddress2;
	
	@JsonProperty(value="お届け先 住所3", index=31)
	private String tdkskAddress3;
	
	@JsonProperty(value="お届け先 電話番号", index=32)
	private String tdkskTelNo;
	
	@JsonProperty(value="お届け先 FAX番号", index=33)
	private String tdkskFaxNo;
	
	@JsonProperty(value="お届け先 メールアドレス", index=34)
	private String tdkskMailAddress;
	
	@JsonProperty(value="合計金額", index=35)
	private String totalKingaku;
	
	@JsonProperty(value="商品合計", index=36)
	private String syohnTotal;
	
	@JsonProperty(value="ポイント利用", index=37)
	private String pointRiyo;
	
	@JsonProperty(value="クーポン利用", index=38)
	private String couponRiyo;
	
	@JsonProperty(value="値引き", index=39)
	private String nebiki;
	
	@JsonProperty(value="送料", index=40)
	private String soryo;
	
	@JsonProperty(value="その他手数料", index=41)
	private String tesuryo;
	
	@JsonProperty(value="税合計", index=42)
	private String zeiTotal;
	
	@JsonProperty(value="支払方法", index=43)
	private String siharaiHoho;
	
	@JsonProperty(value="配送方法", index=44)
	private String haisoHoho;
	
	@JsonProperty(value="配送温度", index=45)
	private String haisoOndo;
	
	@JsonProperty(value="お届け希望日", index=46)
	private String tdkKiboDate;
	
	@JsonProperty(value="お届け希望時間帯", index=47)
	private String tdkKiboJikantai;
	
	@JsonProperty(value="購入者による備考", index=48)
	private String konyusyaBiko;
	
	@JsonProperty(value="マーチャントによる備考", index=49)
	private String marchantBiko;
	
	@JsonProperty(value="出荷指示書 特記事項", index=50)
	private String sykkaSzTokki;
	
	@JsonProperty(value="納品書 特記事項", index=51)
	private String nohinsyoTokki;
	
	@JsonProperty(value="送り状 特記事項", index=52)
	private String okurijoTokki;
	
	@JsonProperty(value="ギフトの指定", index=53)
	private String giftSitei;
	
	@JsonProperty(value="熨斗の指定", index=54)
	private String noshiSitei;
	
	@JsonProperty(value="ラッピングの指定", index=55)
	private String wrappingSitei;
	
	@JsonProperty(value="出荷通知", index=56)
	private String sykkaTuti;
	
	@JsonProperty(value="受注日時", index=57)
	private String juchuDate;
	
	@JsonProperty(value="入金日時", index=58)
	private String nyukinDate;
	
	@JsonProperty(value="完了日時", index=59)
	private String kanryoDate;
	
	@JsonProperty(value="受注明細コード", index=60)
	private String juchuMesiCd;
	
	@JsonProperty(value="受注明細ステータス", index=61)
	private String juchuMesiStatus;
	
	@JsonProperty(value="商品コード", index=62)
	private String syohnCd;
	
	@JsonProperty(value="商品名", index=63)
	private String syohnNm;
	
	@JsonProperty(value="明細行備考欄", index=64)
	private String mesiBiko;
	
	@JsonProperty(value="販売単価", index=65)
	private String hanbaiTnk;
	
	@JsonProperty(value="税区分", index=66)
	private String zeiKb;
	
	@JsonProperty(value="税率", index=67)
	private String zeiRitu;
	
	@JsonProperty(value="数量", index=68)
	private String su;
	
	@JsonProperty(value="小計", index=69)
	private String syokei;
	
	@JsonProperty(value="引当待ち数量", index=70)
	private String hikiateMatiSu;
	
	@JsonProperty(value="引当済み数量", index=71)
	private String hikiateZumiSu;
	
	@JsonProperty(value="親明細", index=72)
	private String oyaMesi;
	
	@JsonProperty(value="子明細", index=73)
	private String koMesi;
	
	@JsonProperty(value="倉庫ID", index=74)
	private String soukoId;
	
	@JsonProperty(value="店舗ID", index=75)
	private String tenpoId;
	
	@JsonProperty(value="店舗名", index=76)
	private String tenpoNm;
	
	@JsonProperty(value="店舗コード", index=77)
	private String tenpoCd;
	
	@JsonProperty(value="プラットフォーム", index=78)
	private String platform;
	
	@JsonProperty(value="ロジレスコード", index=79)
	private String logilessCd;
	
	@JsonProperty(value="フリー１（ＪＡＮ）", index=80)
	private String free1;
	
	@JsonProperty(value="フリー２（商品属性値名称）", index=81)
	private String free2;
	
	@JsonProperty(value="フリー３（商品合計充当額）", index=82)
	private String free3;
	
	@JsonProperty(value="フリー４（パートナーコード）", index=83)
	private String free4;
	
	@JsonProperty(value="フリー５（パートナー名）", index=84)
	private String free5;
	
	@JsonProperty(value="フリー６（ドコモ負担率）", index=85)
	private String free6;
	
	@JsonProperty(value="フリー７（商品単価）", index=86)
	private String free7;
	
	@JsonProperty(value="フリー８（商品金額）", index=87)
	private String free8;
	
	@JsonProperty(value="フリー９（ＳＫＵ）", index=88)
	private String free9;
	
	@JsonProperty(value="フリー１０（トークン）", index=89)
	private String free10;
	
	@JsonProperty(value="(出荷伝）配送追跡番号リスト", index=90)
	private String sykkaDenHaisoTuisekiNoList;
	
	@JsonProperty(value="(出荷伝）配送方法", index=91)
	private String sykkaDenHaisoHoho;
	
	@JsonProperty(value="(出荷伝）配送ステータス", index=92)
	private String sykkaDenHaisoStatus;
	
	@JsonProperty(value="(出荷伝）伝票ステータス", index=93)
	private String sykkaDenDenpyoStatus;
	
	@JsonProperty(value="(出荷伝）お届け希望日", index=94)
	private String sykkaDenTdkKiboDate;
	
	@JsonProperty(value="(出荷伝）お届け希望時間", index=95)
	private String sykkaDenTdkKiboJikantai;
	
	@JsonProperty(value="単価", index=96)
	private String tnk;
	
	@JsonProperty(value="親商品コード", index=97)
	private String oyaSyohnCd;
}
