package logiless.web.model.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import logiless.web.model.dto.BaraItem;
import logiless.web.model.dto.SetItem;
import logiless.web.model.dto.Tenpo;
import logiless.web.model.entity.BaraItemEntity;
import logiless.web.model.entity.SetItemEntity;
import logiless.web.model.entity.TenpoEntity;
import logiless.web.model.form.SetItemForm;
import logiless.web.model.repository.BaraItemRepository;
import logiless.web.model.repository.SetItemRepository;
import logiless.web.model.repository.TenpoRepository;

@Service
@Transactional
public class SetItemService {

	private final TenpoRepository tenpoRepository;
	private final SetItemRepository setItemRepository;
	private final BaraItemRepository baraItemRepository;

	@Autowired
	public SetItemService(TenpoRepository tenpoRepository, SetItemRepository setItemRepository,
			BaraItemRepository baraItemRepository) {
		this.tenpoRepository = tenpoRepository;
		this.setItemRepository = setItemRepository;
		this.baraItemRepository = baraItemRepository;
	}

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
	 * 店舗コード、セット商品コード、セット商品名でセット商品一覧を取得
	 * 
	 * @param tenpoCode
	 * @return
	 */
	public List<SetItem> getSetItemListByCodeAndNameLikeAndTenpoCode(String code, String name, String tenpoCode) {

		SetItemEntity e = new SetItemEntity();
		// TODO ここもっとかっこよくかけたらいいな

		// 空文字を入れると空文字と一致検索しちゃうので、空文字の場合は格納せず
		// 検索条件としない
		if (!StringUtils.isEmpty(code)) {
			e.setCode(code);
		}
		if (!StringUtils.isEmpty(name)) {
			e.setName(name);
		}
		e.setTenpoCode(tenpoCode);

		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", match -> match.contains());

		List<SetItemEntity> entityList = setItemRepository.findAll(Example.of(e, matcher));
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

	/**
	 * セット商品マスターとバラ商品マスターに追加する。
	 * 
	 * @param setItemForm
	 * @return
	 */
	public boolean insertSetItem(SetItemForm setItemForm) {

		SetItemEntity setItemEntity = new SetItemEntity();
		BeanUtils.copyProperties(setItemForm.getSetItem(), setItemEntity);

		try {
			setItemRepository.save(setItemEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		List<BaraItem> baraItemList = setItemForm.getBaraItemList();

		try {
			for (BaraItem baraItem : baraItemList) {
				if (StringUtils.isEmpty(baraItem.getCode())) {
					continue;
				}
				BaraItemEntity baraItemEntity = new BaraItemEntity();
				BeanUtils.copyProperties(baraItem, baraItemEntity);
				baraItemRepository.save(baraItemEntity);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	/**
	 * セット商品マスターとバラ商品マスターを編集する。<br>
	 * セット商品マスターはセット商品名に変更があった時のみ更新する<br>
	 * バラ商品マスターは店舗コードとセット商品コードで検索し、<br>
	 * 対象の明細を全削除した後追加する。
	 * 
	 * @param setItemForm
	 * @return
	 */
	public boolean updateSetItem(SetItemForm setItemForm) {

		SetItemEntity setItemEntity = new SetItemEntity();
		BeanUtils.copyProperties(setItemForm.getSetItem(), setItemEntity);

		try {
			setItemRepository.save(setItemEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		List<BaraItem> baraItemList = setItemForm.getBaraItemList();

		String tenpoCode = setItemForm.getSetItem().getTenpoCode();
		String setItemCode = setItemForm.getSetItem().getCode();

		try {
			baraItemRepository.deleteByTenpoCodeAndSetItemCode(tenpoCode, setItemCode);

			if (baraItemList != null) {

				for (BaraItem baraItem : baraItemList) {
					if (StringUtils.isEmpty(baraItem.getCode())) {
						continue;
					}
					BaraItemEntity baraItemEntity = new BaraItemEntity();
					BeanUtils.copyProperties(baraItem, baraItemEntity);
					baraItemEntity.setTenpoCode(tenpoCode);
					baraItemEntity.setSetItemCode(setItemCode);

					baraItemRepository.save(baraItemEntity);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

}
