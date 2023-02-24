package logiless.common.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OAuthトークンエンティティ
 * 
 * @author nsh14789
 *
 */
@Entity
@Table(name = "API_T_OAUTH_TOKEN")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthTokenEntity {
	@Id
	private String platform;

	private String accessToken;

	private String refreshToken;

}
