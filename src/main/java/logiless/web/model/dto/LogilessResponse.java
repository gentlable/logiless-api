package logiless.web.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// TODO APIのレスポンスはEntityなのか
public class LogilessResponse<T> {
	
	private List<T> data;
	
	@JsonProperty("current_page")
	private int currentPage;
	
	private int limit;
	
	@JsonProperty("total_count")
	private int totalCount;

}
