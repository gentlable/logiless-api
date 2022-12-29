package logiless.web.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import logiless.web.model.dto.Tenpo;
import logiless.web.model.entity.TenpoEntity;
import logiless.web.model.repository.TenpoRepository;

@Service
@Transactional
public class TenpoService {

	@Autowired
	private TenpoRepository tenpoRepository;

	/**
	 * 店舗コードで店舗を取得
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
	 * @return
	 */
	public List<Tenpo> getAllTenpoList() {

		List<TenpoEntity> entityList = tenpoRepository.findAllByOrderByCode();
		List<Tenpo> tenpoList = new ArrayList<Tenpo>();
		for(TenpoEntity entity : entityList) {
			Tenpo tenpo = new Tenpo();
			BeanUtils.copyProperties(entity, tenpo);
			tenpoList.add(tenpo);
		}
		return tenpoList;
	}
}
