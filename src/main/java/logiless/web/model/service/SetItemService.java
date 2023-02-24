package logiless.web.model.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import logiless.web.model.dto.BaraItem;
import logiless.web.model.dto.SetItem;
import logiless.web.model.dto.SetItemCsv;
import logiless.web.model.dto.Tenpo;
import logiless.web.model.dto.compositekey.SetItemCsvCompositeKey;
import logiless.web.model.entity.BaraItemEntity;
import logiless.web.model.entity.SetItemEntity;
import logiless.web.model.entity.TenpoEntity;
import logiless.web.model.form.SetItemForm;
import logiless.web.model.form.SetItemListForm;
import logiless.web.model.form.SetItemUploadForm;
import logiless.web.model.repository.BaraItemRepository;
import logiless.web.model.repository.SetItemRepository;
import logiless.web.model.repository.TenpoRepository;

/**
 * セット商品マスター関連サービス
 * 
 * @author nsh14789
 *
 */
@Service
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

	@InitBinder
	public void configureWebDataBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(1024);
	}

	/**
	 * 店舗コードで店舗を取得
	 * 
	 * @param code
	 * @return
	 */
	public Tenpo getTenpoByCode(String code) {

		TenpoEntity entity = tenpoRepository.findById(code).get();

		if (entity == null) {
			return null;
		}

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

		// 空文字を入れると空文字と一致検索しちゃうので、空文字の場合は格納せず検索条件としない
		if (!StringUtils.isEmpty(code)) {
			e.setCode(code);
		}
		if (!StringUtils.isEmpty(name)) {
			e.setName(name);
		}
		e.setTenpoCode(tenpoCode);

		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", match -> match.contains());

		List<SetItemEntity> entityList = setItemRepository.findAll(Example.of(e, matcher),
				Sort.by(Sort.Direction.ASC, "code"));
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
	 * @param code
	 * @param tenpoCode
	 * @return
	 */
	public SetItem getSetItemByCodeAndTenpoCode(String code, String tenpoCode) {

		SetItemEntity entity = setItemRepository.findByCodeAndTenpoCode(code, tenpoCode);

		if (entity == null) {
			return null;
		}

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
	 * セット商品マスターとバラ商品マスターを更新する。<br>
	 * セット商品マスターはセット商品名に変更があった時のみ更新する<br>
	 * バラ商品マスターは店舗コードとセット商品コードで検索し、<br>
	 * 対象の明細を全削除した後追加する。
	 * 
	 * @param setItemForm
	 * @return
	 */
	public boolean updateSetItem(SetItemForm setItemForm) {

		System.out.println(setItemForm.getSetItem().getCode() + "," + setItemForm.getSetItem().getTenpoCode());

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

	/**
	 * セット商品マスターとバラ商品マスターを削除する。<br>
	 * 
	 * @param setItemForm
	 * @return
	 */
	public boolean deleteSetItem(SetItemListForm setItemListForm) {

		for (SetItem setItem : setItemListForm.getSetItemList()) {

			if (!setItem.isCheck()) {
				continue;
			}

			// 先にバラ商品から削除
			String tenpoCode = setItem.getTenpoCode();
			String setItemCode = setItem.getCode();

			try {
				baraItemRepository.deleteByTenpoCodeAndSetItemCode(tenpoCode, setItemCode);

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

			// セット商品の削除
			SetItemEntity setItemEntity = new SetItemEntity();
			BeanUtils.copyProperties(setItem, setItemEntity);

			try {
				setItemRepository.delete(setItemEntity);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	/**
	 * CSVファイルから一括登録する。
	 * 
	 * @return
	 */
	public boolean uploadSetItem(SetItemUploadForm setItemUploadForm) {

		MultipartFile file = setItemUploadForm.getFile();

		try {

			// 行毎にDTOに格納する。
			List<SetItemCsv> setItemCsvList = new ArrayList<>();

			CsvMapper csvMapper = new CsvMapper();
			CsvSchema csvSchema = csvMapper.schemaFor(SetItemCsv.class).withHeader();
			csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);

			MappingIterator<SetItemCsv> objectMappingIterator = csvMapper.readerFor(SetItemCsv.class).with(csvSchema)
					.readValues(new InputStreamReader(file.getInputStream(), Charset.forName("MS932")));

			while (objectMappingIterator.hasNext()) {
				setItemCsvList.add(objectMappingIterator.next());
			}

			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

			for (SetItemCsv data : setItemCsvList) {
				Set<ConstraintViolation<SetItemCsv>> violations = validator.validate(data);
				if (!violations.isEmpty()) {

					for (ConstraintViolation<SetItemCsv> violation : violations) {
						String message = violation.getMessage();
						Object invalidValue = violation.getInvalidValue();

						// TODO エラーハンドリング
						return false;
					}

				}
			}
			// 店舗コード、セット商品コード事にまとめる
			Map<SetItemCsvCompositeKey, List<SetItemCsv>> groupedSetItemCsvListMap = setItemCsvList.stream().collect(
					Collectors.groupingBy(obj -> new SetItemCsvCompositeKey(obj.getTenpoCode(), obj.getSetItemCode())));

			// CSVDTOからFormに変換する
			List<SetItemForm> setItemFormList = new ArrayList<>();

			groupedSetItemCsvListMap.forEach((key, groupedSetItemCsvList) -> {

				SetItemCsv groupedSetItemCsvHead = groupedSetItemCsvList.get(0);

				SetItem setItem = new SetItem();
				setItem.setCode(groupedSetItemCsvHead.getSetItemCode());
				setItem.setName(groupedSetItemCsvHead.getSetItemName());
				setItem.setTenpoCode(groupedSetItemCsvHead.getTenpoCode());

				List<BaraItem> baraItemList = new ArrayList<>();

				for (SetItemCsv setItemCsv : groupedSetItemCsvList) {

					BaraItem baraItem = new BaraItem();
					baraItem.setCode(setItemCsv.getBaraItemCode());
					baraItem.setName(setItemCsv.getBaraItemName());
					baraItem.setSetItemCode(setItemCsv.getSetItemCode());
					baraItem.setQuantity(setItemCsv.getQuantity());
					baraItem.setPrice(setItemCsv.getPrice());

					baraItemList.add(baraItem);

				}

				SetItemForm setItemForm = new SetItemForm();
				setItemForm.setSetItem(setItem);
				setItemForm.setBaraItemList(baraItemList);
				setItemFormList.add(setItemForm);
			});

			// 更新する。
			for (SetItemForm setItemForm : setItemFormList) {
				if (!updateSetItem(setItemForm)) {
					return false;
				}
			}

		} catch (IOException e) {
			// システムエラー
			e.printStackTrace();
			return false;
		}

		return true;

	}

}
