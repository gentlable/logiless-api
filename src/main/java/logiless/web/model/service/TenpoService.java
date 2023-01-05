package logiless.web.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import logiless.web.model.dto.Tenpo;
import logiless.web.model.entity.TenpoEntity;
import logiless.web.model.form.TenpoListForm;
import logiless.web.model.repository.TenpoRepository;

@Service
@Transactional
public class TenpoService {

	@Autowired
	private TenpoRepository tenpoRepository;

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
	 * @return
	 */
	public boolean insertTenpo(Tenpo tenpo) {

		TenpoEntity entity = new TenpoEntity();
		BeanUtils.copyProperties(tenpo, entity);
		try {
			tenpoRepository.save(entity);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public boolean insertTenpoList(TenpoListForm tenpoListForm) {
		
		List<Tenpo> tenpoList = tenpoListForm.getTenpoList();

		try {
			for (Tenpo tenpo : tenpoList) {
				if(tenpo.getCode() == null || tenpo.getCode().equals("")) {
					continue;
				}
				TenpoEntity entity = new TenpoEntity();
				BeanUtils.copyProperties(tenpo, entity);
				tenpoRepository.save(entity);
			}
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
