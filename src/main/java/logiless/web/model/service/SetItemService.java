package logiless.web.model.service;

import static logiless.common.model.constant.Constant.HAN_SPACE;
import static logiless.common.model.constant.Constant.ZEN_SPACE;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import logiless.common.exception.InvalidInputException;
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
 * ?????????????????????????????????????????????
 * 
 * @author nsh14789
 *
 */
@Service
@Transactional
public class SetItemService {

	private final TenpoRepository tenpoRepository;
	private final SetItemRepository setItemRepository;
	private final BaraItemRepository baraItemRepository;
	private final EntityManager entityManager;

	@Autowired
	public SetItemService(TenpoRepository tenpoRepository, SetItemRepository setItemRepository,
			BaraItemRepository baraItemRepository, EntityManager entityManager) {
		this.tenpoRepository = tenpoRepository;
		this.setItemRepository = setItemRepository;
		this.baraItemRepository = baraItemRepository;
		this.entityManager = entityManager;
	}

	@InitBinder
	public void configureWebDataBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(1024);
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param tenpoCd
	 * @return
	 */
	public Tenpo getTenpoByTenpoCd(String tenpoCd) {

		TenpoEntity entity = tenpoRepository.findById(tenpoCd).get();

		if (entity == null) {
			return null;
		}

		Tenpo tenpo = new Tenpo();
		BeanUtils.copyProperties(entity, tenpo);
		return tenpo;

	}

	/**
	 * ???????????????
	 * 
	 * @return
	 */
	public List<Tenpo> getAllTenpoList() {

		List<TenpoEntity> entityList = tenpoRepository.findAllByOrderByTenpoCd();
		List<Tenpo> tenpoList = new ArrayList<Tenpo>();
		for (TenpoEntity entity : entityList) {
			Tenpo tenpo = new Tenpo();
			BeanUtils.copyProperties(entity, tenpo);
			tenpoList.add(tenpo);
		}
		return tenpoList;
	}

	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @param tenpoCd
	 * @return
	 */
	public List<SetItem> getSetItemListByTenpoCd(String tenpoCd) {

		List<SetItemEntity> entityList = setItemRepository.findByTenpoCdOrderBySetItemCd(tenpoCd);
		List<SetItem> setItemList = new ArrayList<SetItem>();
		for (SetItemEntity entity : entityList) {
			SetItem setItem = new SetItem();
			BeanUtils.copyProperties(entity, setItem);
			setItemList.add(setItem);
		}
		return setItemList;
	}

	/**
	 * ????????????????????????????????????????????????????????????????????????????????????????????????
	 * 
	 * @param tenpoCd
	 * @return
	 */
	public List<SetItem> getSetItemListBySetItemCdAndSetItemNmLikeAndTenpoCd(String setItemCd, String setItemNm,
			String tenpoCd) {

		SetItemEntity e = new SetItemEntity();

		e.setSetItemCd(setItemCd);
		e.setSetItemNm(setItemNm);
		e.setTenpoCd(tenpoCd);

		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("setItemNm", match -> match.contains());

		List<SetItemEntity> entityList = setItemRepository.findAll(Example.of(e, matcher),
				Sort.by(Sort.Direction.ASC, "setItemCd"));
		List<SetItem> setItemList = new ArrayList<>();
		for (SetItemEntity entity : entityList) {
			SetItem setItem = new SetItem();
			BeanUtils.copyProperties(entity, setItem);
			setItemList.add(setItem);
		}
		return setItemList;
	}

	/**
	 * ?????????????????????????????????????????????????????????????????????
	 * 
	 * @param setItemCd
	 * @param tenpoCd
	 * @return
	 */
	public SetItem getSetItemBySetItemCdAndTenpoCd(String setItemCd, String tenpoCd) {

		SetItemEntity entity = setItemRepository.findBySetItemCdAndTenpoCd(setItemCd, tenpoCd);

		if (entity == null) {
			return null;
		}

		SetItem setItem = new SetItem();
		BeanUtils.copyProperties(entity, setItem);
		return setItem;
	}

	/**
	 * ????????????????????????????????????????????????????????????????????????
	 * 
	 * @param tenpoCd
	 * @param setItemCd
	 * @return
	 */
	public List<BaraItem> getBaraItemByTenpoCdAndSetItemCd(String tenpoCd, String setItemCd) {
		List<BaraItemEntity> entityList = baraItemRepository.findByTenpoCdAndSetItemCdOrderByBaraItemCd(tenpoCd,
				setItemCd);
		List<BaraItem> baraItemList = new ArrayList<BaraItem>();
		for (BaraItemEntity entity : entityList) {
			BaraItem baraItem = new BaraItem();
			BeanUtils.copyProperties(entity, baraItem);
			baraItemList.add(baraItem);
		}
		return baraItemList;
	}

	/**
	 * ????????????????????????????????????????????????????????????????????????
	 * 
	 * @param setItemForm
	 * @return
	 */
	public boolean insertSetItem(SetItemForm setItemForm) {

		SetItem setItem = setItemForm.getSetItem();

		SetItemEntity setItemEntity = new SetItemEntity();
		BeanUtils.copyProperties(setItem, setItemEntity);

		// NCHAR?????????????????????????????????
		setItemEntity.setSetItemNm(StringUtils.rightPad(setItemEntity.getSetItemNm(), 100, ZEN_SPACE));

		try {
			setItemRepository.save(setItemEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		List<BaraItem> baraItemList = setItemForm.getBaraItemList();

		try {
			for (BaraItem baraItem : baraItemList) {
				baraItem.setTenpoCd(setItem.getTenpoCd());
				if (StringUtils.isEmpty(baraItem.getBaraItemCd())) {
					continue;
				}
				BaraItemEntity baraItemEntity = new BaraItemEntity();
				BeanUtils.copyProperties(baraItem, baraItemEntity);
				// ??????????????????null?????????????????????????????????????????????????????????
				baraItemEntity.setBaraItemNm(StringUtils.isEmpty(baraItemEntity.getBaraItemNm()) ? HAN_SPACE
						: baraItemEntity.getBaraItemNm());
				baraItemRepository.save(baraItemEntity);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * ????????????????????????????????????????????????????????????????????????<br>
	 * ??????????????????????????????????????????????????????????????????????????????????????????<br>
	 * ????????????????????????????????????????????????????????????????????????????????????<br>
	 * ???????????????????????????????????????????????????
	 * 
	 * @param setItemForm
	 * @return
	 */
	public boolean updateSetItem(SetItemForm setItemForm) throws NoResultException {

		SetItem setItem = setItemForm.getSetItem();

		// ????????????????????????
		String jpql = "SELECT e FROM SetItemEntity e WHERE e.setItemCd = :setItemCd AND e.tenpoCd = :tenpoCd AND e.version = :version";

		try {

			SetItemEntity setItemEntity = entityManager.createQuery(jpql, SetItemEntity.class)
					.setParameter("setItemCd", setItem.getSetItemCd()).setParameter("tenpoCd", setItem.getTenpoCd())
					.setParameter("version", setItem.getVersion()).getSingleResult();

			setItemEntity.setSetItemCd(setItem.getSetItemCd());
			setItemEntity.setTenpoCd(setItem.getTenpoCd());
			// NCHAR?????????????????????????????????
			setItemEntity.setSetItemNm(StringUtils.rightPad(setItemEntity.getSetItemNm(), 100, ZEN_SPACE));

			entityManager.merge(setItemEntity);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		List<BaraItem> baraItemList = setItemForm.getBaraItemList();

		String tenpoCd = setItemForm.getSetItem().getTenpoCd();
		String setItemCd = setItemForm.getSetItem().getSetItemCd();

		try {
			baraItemRepository.deleteByTenpoCdAndSetItemCd(tenpoCd, setItemCd);

			if (baraItemList != null) {

				for (BaraItem baraItem : baraItemList) {
					if (StringUtils.isEmpty(baraItem.getBaraItemCd())) {
						continue;
					}
					BaraItemEntity baraItemEntity = new BaraItemEntity();
					BeanUtils.copyProperties(baraItem, baraItemEntity);
					baraItemEntity.setTenpoCd(tenpoCd);
					baraItemEntity.setSetItemCd(setItemCd);

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
	 * ????????????????????????????????????????????????????????????????????????<br>
	 * 
	 * @param setItemForm
	 * @return
	 */
	public boolean deleteSetItem(SetItemListForm setItemListForm) {

		for (SetItem setItem : setItemListForm.getSetItemList()) {

			if (!setItem.isCheck()) {
				continue;
			}

			// ??????????????????????????????
			String tenpoCd = setItem.getTenpoCd();
			String setItemCd = setItem.getSetItemCd();

			try {
				baraItemRepository.deleteByTenpoCdAndSetItemCd(tenpoCd, setItemCd);

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

			// ????????????????????????
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
	 * CSV???????????????????????????????????????
	 * 
	 * @return
	 * @throws InvalidInputException
	 */
	public boolean uploadSetItem(SetItemUploadForm setItemUploadForm) throws InvalidInputException {

		MultipartFile file = setItemUploadForm.getFile();

		try {

			// ?????????DTO??????????????????
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

			for (SetItemCsv setItemCsv : setItemCsvList) {
				Set<ConstraintViolation<SetItemCsv>> violations = validator.validate(setItemCsv);
				if (!violations.isEmpty()) {

					for (ConstraintViolation<SetItemCsv> violation : violations) {
						String message = violation.getMessage();
						Object invalidValue = violation.getInvalidValue();

						throw new InvalidInputException(message);
					}
				}
			}

			// ????????????????????????????????????????????????????????????
			Map<SetItemCsvCompositeKey, List<SetItemCsv>> groupedSetItemCsvListMap = setItemCsvList.stream().collect(
					Collectors.groupingBy(obj -> new SetItemCsvCompositeKey(obj.getTenpoCd(), obj.getSetItemCd())));

			// CSVDTO??????Form???????????????
			List<SetItemForm> setItemFormList = new ArrayList<>();

			groupedSetItemCsvListMap.forEach((key, groupedSetItemCsvList) -> {

				SetItemCsv groupedSetItemCsvHead = groupedSetItemCsvList.get(0);

				SetItem setItem = new SetItem();
				setItem.setSetItemCd(groupedSetItemCsvHead.getSetItemCd());
				setItem.setSetItemNm(groupedSetItemCsvHead.getSetItemNm());
				setItem.setTenpoCd(groupedSetItemCsvHead.getTenpoCd());

				List<BaraItem> baraItemList = new ArrayList<>();

				for (SetItemCsv setItemCsv : groupedSetItemCsvList) {

					BaraItem baraItem = new BaraItem();
					baraItem.setBaraItemCd(setItemCsv.getBaraItemCd());
					baraItem.setBaraItemNm(setItemCsv.getBaraItemNm());
					baraItem.setSetItemCd(setItemCsv.getSetItemCd());
					baraItem.setQuantity(setItemCsv.getQuantity());
					baraItem.setPrice(setItemCsv.getPrice());

					baraItemList.add(baraItem);
				}

				SetItemForm setItemForm = new SetItemForm();
				setItemForm.setSetItem(setItem);
				setItemForm.setBaraItemList(baraItemList);
				setItemFormList.add(setItemForm);
			});

			// ??????????????????????????????????????????????????????????????????????????????????????????????????????
			// List??????Set??????????????????????????????validator?????????????????????
			for (SetItemForm setItemForm : setItemFormList) {

				Set<ConstraintViolation<SetItemForm>> violations = validator.validate(setItemForm);
				if (!violations.isEmpty()) {

					for (ConstraintViolation<SetItemForm> violation : violations) {
						String message = violation.getMessage();
						Object invalidValue = violation.getInvalidValue();

						throw new InvalidInputException(message);
					}
				}
			}

			// ???????????????
			for (SetItemForm setItemForm : setItemFormList) {
				if (!insertSetItem(setItemForm)) {
					return false;
				}
			}

		} catch (IOException e) {
			// ?????????????????????
			e.printStackTrace();
			return false;
		}

		return true;

	}

}
