package logiless.web.model.entity.primarykey;

import java.io.Serializable;

import javax.persistence.Id;

public class BaraItemPKEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	@Id
	private String tenpoCode;

	@Id
	private String setItemCode;
}
