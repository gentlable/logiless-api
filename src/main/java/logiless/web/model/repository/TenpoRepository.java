package logiless.web.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import logiless.web.model.entity.TenpoEntity;

/**
 * ロジレス店舗マスタ－レポジトリ
 * 
 * @author nsh14789
 *
 */
@Transactional
@Repository
public interface TenpoRepository extends JpaRepository<TenpoEntity, String> {
	/**
	 * 店舗コード昇順で全店舗を返却
	 * 
	 * @return
	 */
	List<TenpoEntity> findAllByOrderByTenpoCd();
}
