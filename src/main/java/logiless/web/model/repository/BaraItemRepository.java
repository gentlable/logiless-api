package logiless.web.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import logiless.web.model.entity.BaraItemEntity;

@Repository
public interface BaraItemRepository extends JpaRepository<BaraItemEntity, String> {

	/**
	 * 店舗コードとセット商品コードをキーにバラ商品昇順を取得
	 * 
	 * @param tenpoCode
	 * @param setItemCode
	 * @return
	 */
	List<BaraItemEntity> findByTenpoCodeAndSetItemCodeOrderByCode(String tenpoCode, String setItemCode);

	/**
	 * 店舗コードとセット商品コードをキーにバラ商品を削除
	 * 
	 * @param tenpoCode
	 * @param setItemCode
	 * @return
	 */
	List<BaraItemEntity> deleteByTenpoCodeAndSetItemCode(String tenpoCode, String setItemCode);
}
