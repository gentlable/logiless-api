package logiless.ademo.democsv;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoCsvRecord {

	@JsonDeserialize(using = StringDeserializer.class)
	@JsonProperty(index = 0)
	private String id;
	@JsonDeserialize(using = StringDeserializer.class)
	@JsonProperty(index = 1)
	private String name;
	@JsonDeserialize(using = StringDeserializer.class)
	@JsonProperty(index = 2)
	private String nulll;
	@JsonDeserialize(using = StringDeserializer.class)
	@JsonProperty(index = 3)
	private String empty;

}

class StringDeserializer extends JsonDeserializer<String> {
	@Override
	public String deserialize(com.fasterxml.jackson.core.JsonParser p,
			com.fasterxml.jackson.databind.DeserializationContext ctxt)
			throws IOException, com.fasterxml.jackson.core.JsonProcessingException {
		String value = p.getValueAsString();
		return value == null ? "" : value;
	}
}