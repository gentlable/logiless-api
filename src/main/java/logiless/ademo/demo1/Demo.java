package logiless.ademo.demo1;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Demo {
	public static void main(String[] args) throws Exception {

		String jsonData = "{\"id\": \"1\",\"name\": \"suzuki\",\"sikaku\": [\"基本\",\"応用\"]}";
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			SyainData syain = mapper.readValue(jsonData, SyainData.class);
			
			System.out.println(syain.getId());
			System.out.println(syain.getRomaji());
			System.out.println(syain.getSikaku().get(0));
			System.out.println(syain.getSikaku().get(1));

		} catch (JsonParseException e) {
			e.printStackTrace();

		} catch (JsonMappingException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
