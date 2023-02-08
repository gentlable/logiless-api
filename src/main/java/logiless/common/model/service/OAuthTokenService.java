package logiless.common.model.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import logiless.common.model.dto.oAuth.OAuthToken;
import logiless.common.model.dto.repository.OAuthTokenRepository;
import logiless.common.model.entity.OAuthTokenEntity;

@Service
public class OAuthTokenService {

	private final OAuthTokenRepository oAuthTokenRepository;

	@Autowired
	public OAuthTokenService(OAuthTokenRepository oAuthTokenRepository) {
		this.oAuthTokenRepository = oAuthTokenRepository;
	}

	/**
	 * 各プラットフォームのOAuthトークンを取得
	 * 
	 * @param platform
	 * @return
	 */
	public OAuthToken getOAuthTokenByPlatform(String platform) {

		OAuthTokenEntity entity = oAuthTokenRepository.findByPlatform(platform);
		OAuthToken oAuthToken = new OAuthToken();
		BeanUtils.copyProperties(entity, oAuthToken);
		return oAuthToken;
	}

	/**
	 * 各プラットフォームのOAuthトークンを追加、更新する
	 * 
	 * @param platform
	 * @param accessToken
	 * @param refreshToken
	 * @return
	 */
	public boolean save(String platform, String accessToken, String refreshToken) {

		OAuthTokenEntity entity = new OAuthTokenEntity();
		// TODO 仕様考えて修正
		entity.setId(1);
		entity.setPlatform(platform);
		entity.setAccessToken(accessToken);
		entity.setRefreshToken(refreshToken);
		try {
			oAuthTokenRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
