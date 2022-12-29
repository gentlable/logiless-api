package logiless.web.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import logiless.web.model.entity.TenpoEntity;

@Repository
public interface TenpoRepository extends JpaRepository<TenpoEntity, String> {
	/**
	 * 店舗コード昇順で全店舗を返却
	 * @return
	 */
	List<TenpoEntity> findAllByOrderByCode();
}
