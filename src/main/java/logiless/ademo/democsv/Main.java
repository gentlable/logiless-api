package logiless.ademo.democsv;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Main {
	public static void main(String[] args) throws Exception {
		String jsonString = "{\"key\": null}";
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		MyClass obj = mapper.readValue(jsonString, MyClass.class);
		System.out.println(obj.getKey()); // ""
	}
}

class MyClass {
	@JsonDeserialize(using = StringDeserializer.class)
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}

class StringDeserializer1 extends JsonDeserializer<String> {
	@Override
	public String deserialize(com.fasterxml.jackson.core.JsonParser p,
			com.fasterxml.jackson.databind.DeserializationContext ctxt)
			throws IOException, com.fasterxml.jackson.core.JsonProcessingException {
		String value = p.getValueAsString();
		return value == null ? "" : value;
	}
}
