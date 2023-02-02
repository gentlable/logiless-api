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
import logiless.common.model.dto.common.SessionComponent;
import logiless.common.model.dto.juchu.JuchuCsv;
import logiless.common.model.dto.juchu.JuchuDenpyo;
import logiless.web.model.service.OAuth2Service;

@Service
public class LogilessApiService {

	private final SessionComponent sessionComponent;
	private final JuchuCsvConvertService juchuCsvConvertService;
	private final FileOutputService fileOutputService;
	private final OAuth2Service oauth2Service;

	@Autowired
	public LogilessApiService(SessionComponent sessionComponent, JuchuCsvConvertService juchuCsvConvertService,
			FileOutputService fileOutputService, OAuth2Service oauth2Service) {
		this.sessionComponent = sessionComponent;
		this.juchuCsvConvertService = juchuCsvConvertService;
		this.fileOutputService = fileOutputService;
		this.oauth2Service = oauth2Service;
	}

	private static final String API_ENDPOINT = "https://app2.logiless.com/api/v1/merchant/{merchant_id}";

	private static final String merchantId = "1022";
	private static final String FINISH_TIME = "15:30:00";

	/**
	 * 受注伝票データを取得（出荷実績）
	 */
	public boolean getJuchu(String syoriDt, String tenpoCd) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = null;

		if (syoriDt == null) {
			date = LocalDate.now();
		} else {
			date = LocalDate.parse(syoriDt, formatter);
		}

		String endpoint = API_ENDPOINT + "/sales_orders?limit=100";
		endpoint += "&page={page}"; // ステータス出荷済み
		endpoint += "&document_status={document_status}"; // ステータス出荷済み
		endpoint += "&finished_at_from={finished_at_from}"; // 出荷完了日時from
		endpoint += "&finished_at_to={finished_at_to}"; // 出荷完了日時from
		endpoint += "&store={store}";
		date.format(formatter);

		String documentStatus = "Shipped";
		String finishedAtFrom = date.minusDays(1).format(formatter) + " " + FINISH_TIME;
		String finishedAtTo = date.plusDays(1).format(formatter) + " " + FINISH_TIME;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + sessionComponent.getAccessToken());

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
			// XXX 効いていない
			csvMapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
			CsvSchema schema = csvMapper.schemaFor(JuchuCsv.class).withHeader();

			List<JuchuDenpyo> newJuchuDenpyoList = new ArrayList<>();

			fileOutputService.output("logiless_demo", csvMapper.writer(schema)
					.writeValueAsString(juchuCsvConvertService.juchuCsvConvert(juchuDenpyoList)));

			// セット商品分割処理
			for (JuchuDenpyo juchuDenpyo : juchuDenpyoList) {

				newJuchuDenpyoList.add(juchuCsvConvertService.addBaraItem1(juchuDenpyo));

			}

			fileOutputService.output("logiless_sykkaJisseki_seikatuIchiba", csvMapper.writer(schema)
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

}
