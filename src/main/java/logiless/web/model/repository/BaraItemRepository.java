package logiless.web.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import logiless.web.model.entity.BaraItemEntity;

/**
 * ロジレスバラ商品マスタ－レポジトリ
 * 
 * @author nsh14789
 *
 */
@Transactional
@Repository
public interface BaraItemRepository extends JpaRepository<BaraItemEntity, String> {

	/**
	 * 店舗コードとセット商品コードをキーにバラ商品昇順を取得
	 * 
	 * @param tenpoCd
	 * @param setItemCd
	 * @return
	 */
	List<BaraItemEntity> findByTenpoCdAndSetItemCdOrderByBaraItemCd(String tenpoCd, String setItemCd);

	/**
	 * 店舗コードとセット商品コードをキーにバラ商品を削除
	 * 
	 * @param tenpoCd
	 * @param setItemCd
	 * @return
	 */
	List<BaraItemEntity> deleteByTenpoCdAndSetItemCd(String tenpoCd, String setItemCd);
}
