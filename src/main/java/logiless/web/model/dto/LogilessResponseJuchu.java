package logiless.web.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import logiless.common.model.dto.Juchu.JuchuDenpyo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogilessResponseJuchu {
	
	private List<JuchuDenpyo> data;
	
	@JsonProperty("current_page")
	private int currentPage;
	
	private int limit;
	
	@JsonProperty("total_count")
	private int totalCount;

}
