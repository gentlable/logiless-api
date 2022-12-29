package logiless.ademo.demo1;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SyainData {

	private String id;
	
	@JsonProperty("name")
	private String romaji;

	private List<String> sikaku;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getRomaji() {
		return romaji;
	}

	public void setRomaji(String romaji) {
		this.romaji = romaji;
	}

	public List<String> getSikaku() {
		return sikaku;
	}

	public void setSikaku(List<String> sikaku) {
		this.sikaku = sikaku;
	}

}
