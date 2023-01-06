package logiless.web.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import logiless.web.model.entity.TenpoEntity;

@Transactional
@Repository
public interface TenpoRepository extends JpaRepository<TenpoEntity, String> {
	/**
	 * 店舗コード昇順で全店舗を返却
	 * @return
	 */
	List<TenpoEntity> findAllByOrderByCode();
	
//	@Modifying
//	@Query("update api_m_tenpo u set name = :name where code = :code")
//	void setName(@Param("name") String name, @Param("code") String code);
}
