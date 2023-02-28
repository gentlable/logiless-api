package logiless.common.model.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import logiless.common.model.dto.common.LogilessResponse;
import logiless.common.model.dto.juchu.JuchuCsv;
import logiless.common.model.dto.juchu.JuchuDenpyo;

/**
 * ロジレスAPIサービス
 * 
 * @author nsh14789
 *
 */
@Service
public class LogilessApiService {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final JuchuCsvConvertService juchuCsvConvertService;
	private final FileOutputService fileOutputService;
	private final OAuthService oAuthService;
	private final OAuthTokenService oAuthTokenService;

	@Autowired
	public LogilessApiService(JuchuCsvConvertService juchuCsvConvertService, FileOutputService fileOutputService,
			OAuthService oAuthService, OAuthTokenService oAuthTokenService) {
		this.juchuCsvConvertService = juchuCsvConvertService;
		this.fileOutputService = fileOutputService;
		this.oAuthService = oAuthService;
		this.oAuthTokenService = oAuthTokenService;
	}

	private static final String API_ENDPOINT = "https://app2.logiless.com/api/v1/merchant/{merchant_id}";

	private static final String merchantId = "1022";
	private static final String FINISH_TIME = "15:30:00";

	/**
	 * 出荷実績を取得<br>
	 * 指定されたファイル名でCSV形式で出力する。
	 * 
	 * @param syoriDt
	 * @param tenpoCd
	 * @param filname
	 * @return
	 */
	public boolean getJuchu(LocalDate syoriDt, String tenpoCd, String filename) {

		if (syoriDt == null) {
			syoriDt = LocalDate.now();
		}

		String endpoint = API_ENDPOINT + "/sales_orders?limit=100";
		endpoint += "&page={page}"; // ステータス出荷済み
		endpoint += "&document_status={document_status}"; // ステータス出荷済み
		endpoint += "&finished_at_from={finished_at_from}"; // 出荷完了日時from
		endpoint += "&finished_at_to={finished_at_to}"; // 出荷完了日時from
		endpoint += "&store={store}";
		syoriDt.format(DATE_FORMATTER);

		String documentStatus = "Shipped";
		String finishedAtFrom = syoriDt.minusDays(1).format(DATE_FORMATTER) + " " + FINISH_TIME;
		String finishedAtTo = syoriDt.format(DATE_FORMATTER) + " " + FINISH_TIME;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Bearer " + oAuthTokenService.getOAuthTokenByPlatform("logiless").getAccessToken());

		try {

			RestTemplate rest = new RestTemplate();

			List<JuchuDenpyo> juchuDenpyoList = new ArrayList<>();

			int page = 1;

			while (true) {

				RequestEntity<?> req = RequestEntity
						.get(endpoint, merchantId, page, documentStatus, finishedAtFrom, finishedAtTo, tenpoCd)
						.headers(headers).build();

				ResponseEntity<String> res = rest.exchange(req, String.class);
				String json = res.getBody();

				ObjectMapper objectMapper = new ObjectMapper();

				LogilessResponse<JuchuDenpyo> response = objectMapper
						.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
						.readValue(json, new TypeReference<LogilessResponse<JuchuDenpyo>>() {
						});

				// TODO ステータスコードでエラーハンドリングしないといけない

				List<JuchuDenpyo> data = response.getData();

				juchuDenpyoList.addAll(data);

				if (!(data.size() == 100)) {
					break;
				}
				page++;
			}

			CsvMapper csvMapper = new CsvMapper();
			csvMapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
			CsvSchema schema = csvMapper.schemaFor(JuchuCsv.class).withHeader();

			List<JuchuDenpyo> newJuchuDenpyoList = new ArrayList<>();

			// セット商品分割処理
			for (JuchuDenpyo juchuDenpyo : juchuDenpyoList) {

				newJuchuDenpyoList.add(juchuCsvConvertService.addBaraItem(juchuDenpyo));
			}

			fileOutputService.output(filename, csvMapper.writer(schema)
					.writeValueAsString(juchuCsvConvertService.juchuCsvConvert(newJuchuDenpyoList)));

		} catch (HttpClientErrorException e) {

			e.printStackTrace();
			return false;

		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return false;

		}

		return true;
	}

	/**
	 * 受注伝票データを取得（出荷実績）
	 * 
	 * @param syoriDt
	 * @param tenpoCd
	 * @return
	 */
	public boolean getJuchu(String syoriDt, String tenpoCd) {
		return getJuchu(LocalDate.parse(syoriDt, DATE_FORMATTER), tenpoCd, "CDX.STACK.FFAASSJ1");
	}

	/**
	 * 受注伝票データを取得（出荷実績）
	 * 
	 * @param syoriDt
	 * @param tenpoCd
	 * @return
	 */
	public boolean getJuchu(LocalDate syoriDt, String tenpoCd) {
		return getJuchu(syoriDt, tenpoCd, "CDX.STACK.FFAASSJ1");
	}

}
