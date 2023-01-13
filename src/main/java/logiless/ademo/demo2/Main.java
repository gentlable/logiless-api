package logiless.ademo.demo2;

import com.fasterxml.jackson.databind.ObjectMapper;

import logiless.ademo.demo2.model.dto.FavoriteFood;
import logiless.ademo.demo2.model.dto.User;
import logiless.ademo.demo2.model.dto.UserGen;
import net.bytebuddy.description.method.MethodDescription.TypeToken;

public class Main {
	
	public static final String JSON = "{\r\n"
			+ "  \"name\" : \"taro\",\r\n"
			+ "  \"information\" : {\r\n"
			+ "    \"age\": 19, \r\n"
			+ "    \"address\": \"Tokyo\", \r\n"
			+ "    \"hobby\": \"game\"\r\n"
			+ "  },\r\n"
			+ "  \"favoriteFood\" : [\r\n"
			+ "    {\r\n"
			+ "      \"japanese\": \"sushi\", \r\n"
			+ "      \"italian\": \"pizza\"\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "      \"japanese\": \"tempura\", \r\n"
			+ "      \"italian\": \"pasta\"\r\n"
			+ "    }\r\n"
			+ "  ]\r\n"
			+ "}";
	
	public static void main(String[] args) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			User user = mapper.readValue(JSON, User.class);
//			UserGen<FavoriteFood> userGen = mapper.readValue(JSON, UserGen.class);
			UserGen<FavoriteFood> userGen = mapper.readValue(JSON, (Class<UserGen<FavoriteFood>>)(Object)UserGen.class);
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
