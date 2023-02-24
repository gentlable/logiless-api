package logiless.common.model.dto.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * レスポンスDTO
 * 
 * @author nsh14789
 *
 * @param <T>
 */
@Getter
@Setter
public class LogilessResponse<T> {

	private List<T> data;

	@JsonProperty("current_page")
	private int currentPage;

	private int limit;

	@JsonProperty("total_count")
	private int totalCount;

}
