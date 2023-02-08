package logiless.common.model.dto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import logiless.common.model.entity.OAuthTokenEntity;

@Repository
public interface OAuthTokenRepository extends JpaRepository<OAuthTokenEntity, String> {

	// select
	OAuthTokenEntity findByPlatform(String platform);

}
