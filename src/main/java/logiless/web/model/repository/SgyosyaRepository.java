package logiless.web.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import logiless.web.model.entity.SgyosyaEntity;

/**
 * 作業者マスターレポジトリ
 * 
 * @author nsh14789
 *
 */
@Repository
public interface SgyosyaRepository extends JpaRepository<SgyosyaEntity, String> {

	@Query(value = "SELECT SGYOSYA_CD, SGYOSYA_NM, WINS_DECRYPT(A.PASSWORD_CD) AS PASSWORD_CD, SIYOU_TEISI_FL FROM M_SGYOSYA A WHERE A.SGYOSYA_CD = :sgyosyaCd", nativeQuery = true)
	public SgyosyaEntity findDecryptedPasswordCdBySgyosyaCd(@Param("sgyosyaCd") String sgyosyaCd);

}
