package logiless.web.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import logiless.web.model.dto.BaraItem;
import logiless.web.model.dto.SetItem;
import logiless.web.model.dto.Tenpo;
import logiless.web.model.entity.BaraItemEntity;
import logiless.web.model.entity.SetItemEntity;
import logiless.web.model.entity.TenpoEntity;
import logiless.web.model.repository.BaraItemRepository;
import logiless.web.model.repository.SetItemRepository;
import logiless.web.model.repository.TenpoRepository;

@Service
@Transactional
public class SetItemService {

	@Autowired
	private TenpoRepository tenpoRepository;

	@Autowired
	private SetItemRepository setItemRepository;

	@Autowired
	private BaraItemRepository baraItemRepository;

	/**
	 * 店舗コードで店舗を取得
	 * 
	 * @param code
	 * @return
	 */
	public Tenpo getTenpoByCode(String code) {

		TenpoEntity entity = tenpoRepository.findById(code).get();
		Tenpo tenpo = new Tenpo();
		BeanUtils.copyProperties(entity, tenpo);
		return tenpo;

	}

	/**
	 * 全店舗取得
	 * 
	 * @return
	 */
	public List<Tenpo> getAllTenpoList() {

		List<TenpoEntity> entityList = tenpoRepository.findAllByOrderByCode();
		List<Tenpo> tenpoList = new ArrayList<Tenpo>();
		for (TenpoEntity entity : entityList) {
			Tenpo tenpo = new Tenpo();
			BeanUtils.copyProperties(entity, tenpo);
			tenpoList.add(tenpo);
		}
		return tenpoList;
	}

	/**
	 * 店舗コードでセット商品一覧を取得
	 * 
	 * @param tenpoCode
	 * @return
	 */
	public List<SetItem> getSetItemListByTenpoCode(String tenpoCode) {

		List<SetItemEntity> entityList = setItemRepository.findByTenpoCodeOrderByCode(tenpoCode);
		List<SetItem> setItemList = new ArrayList<SetItem>();
		for (SetItemEntity entity : entityList) {
			SetItem setItem = new SetItem();
			BeanUtils.copyProperties(entity, setItem);
			setItemList.add(setItem);
		}
		return setItemList;
	}

	/**
	 * 店舗コード、セット商品コードでセット商品を取得
	 * 
	 * @param tenpoCode
	 * @return
	 */
	public SetItem getSetItemByCodeAndTenpoCode(String code, String tenpoCode) {

		SetItemEntity entity = setItemRepository.findByCodeAndTenpoCode(code, tenpoCode);
		SetItem setItem = new SetItem();
		BeanUtils.copyProperties(entity, setItem);
		return setItem;
	}

	/**
	 * 店舗コード、セット商品コードでバラ商品一覧を取得
	 * 
	 * @param tenpoCode
	 * @param setItemCode
	 * @return
	 */
	public List<BaraItem> getBaraItemByTenpoCodeAndSetItemCode(String tenpoCode, String setItemCode) {

		List<BaraItemEntity> entityList = baraItemRepository.findByTenpoCodeAndSetItemCodeOrderByCode(tenpoCode,
				setItemCode);
		List<BaraItem> baraItemList = new ArrayList<BaraItem>();
		for (BaraItemEntity entity : entityList) {
			BaraItem baraItem = new BaraItem();
			BeanUtils.copyProperties(entity, baraItem);
			baraItemList.add(baraItem);
		}
		return baraItemList;
	}

	// 共通化？
//	private <T, O> List<O> test(O dto, List<T> entityList){
//		
//		List<T> dtoList = new ArrayList<T>();
//		for(T entity : entityList) {
//			O o = new O();
//			BeanUtils.copyProperties(o, entity);
//			dtoList.add(o);
//		}
//		return null;
//	}
}
