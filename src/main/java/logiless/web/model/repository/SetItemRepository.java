package logiless.web.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import logiless.web.model.entity.SetItemEntity;

/**
 * セット商品マスターレポジトリ
 * 
 * @author nsh14789
 *
 */
@Repository
public interface SetItemRepository extends JpaRepository<SetItemEntity, String> {

	/**
	 * 店舗コードをキーにセット商品昇順を取得
	 * 
	 * @return
	 */
	List<SetItemEntity> findByTenpoCodeOrderByCode(String tenpoCode);

	/**
	 * 店舗コードとセット商品コードとセット商品名からセット商品を取得
	 * 
	 * @return
	 */
	List<SetItemEntity> findByCodeAndNameLikeAndTenpoCodeOrderByCode(String code, String name, String tenpoCode);

	/**
	 * 店舗コードとセット商品コードからセット商品を取得
	 * 
	 * @return
	 */
	SetItemEntity findByCodeAndTenpoCode(String code, String tenpoCode);
}
