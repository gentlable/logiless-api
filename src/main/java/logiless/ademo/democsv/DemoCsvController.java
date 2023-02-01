package logiless.ademo.democsv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import logiless.common.model.service.FileOutputService;

@Controller
public class DemoCsvController {

	private final FileOutputService fileOutputService;

	@Autowired
	public DemoCsvController(FileOutputService fileOutputService) {
		this.fileOutputService = fileOutputService;
	}

	private static final String json = "{\r\n" + "  \"id\": \"0001\",\r\n" + "  \"name\": \"test\",\r\n"
			+ "  \"nulll\": null,\r\n" + "  \"empty\": \"\"\r\n" + "}\r\n" + "";

	@GetMapping("/democsv")
	public String getDemo() {

		ObjectMapper objectMapper = new ObjectMapper();

//		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

		try {
			DemoCsvRecord csv = objectMapper.readValue(json, DemoCsvRecord.class);

			String s = objectMapper.writeValueAsString(csv);

			CsvMapper csvMapper = new CsvMapper();
			csvMapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
			CsvSchema schema = csvMapper.schemaFor(DemoCsvRecord.class).withHeader();

			fileOutputService.output("demo-csv", csvMapper.writer(schema).writeValueAsString(csv));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "index";

	}

}
