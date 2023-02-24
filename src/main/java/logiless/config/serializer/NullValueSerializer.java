package logiless.config.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * TODO 動いているかチェック
 * 
 * @author nsh14789
 *
 */
public class NullValueSerializer extends JsonSerializer<Object> {
	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeString("");
	}
}