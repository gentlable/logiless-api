package logiless.common.model.dto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import logiless.common.model.entity.CodeMasterEntity;

/**
 * コード定義マスタレポジトリ
 * 
 * @author nsh14789
 *
 */
public interface CodeMasterRepository {

	@Query("SELECT u FROM Master u WHERE u.categoryCd = :categoryCd")
	List<CodeMasterEntity> masterList(@Param("categoryCd") String categoryCd);

}
