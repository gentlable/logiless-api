package logiless.common.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import logiless.common.model.dto.repository.CodeMasterRepository;
import logiless.common.model.entity.CodeMasterEntity;

public class CodeMasterService {
	
	@Autowired
	CodeMasterRepository codeMasterRepository;
	
	public Map<String, String> codeMasterMap(String categoryCd) {
		
		List<CodeMasterEntity> list = codeMasterRepository.masterList(categoryCd);

        Map<String, String> map = new HashMap<>();

        for (CodeMasterEntity item : list) {
            map.put(item.getOutputCd(), item.getOutputName());
        }

        return map;
	}

}
