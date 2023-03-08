package logiless.web.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import logiless.web.model.entity.SetItemEntity;

/**
 * ロジレスセット商品マスターレポジトリ
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
	List<SetItemEntity> findByTenpoCdOrderBySetItemCd(String tenpoCd);

	/**
	 * 店舗コードとセット商品コードとセット商品名からセット商品を取得
	 * 
	 * @return
	 */
	List<SetItemEntity> findBySetItemCdAndSetItemNmLikeAndTenpoCdOrderBySetItemCd(String setItemCd, String setItemNm,
			String tenpoCd);

	/**
	 * 店舗コードとセット商品コードからセット商品を取得
	 * 
	 * @return
	 */
	SetItemEntity findBySetItemCdAndTenpoCd(String setItemCd, String tenpoCd);
}
