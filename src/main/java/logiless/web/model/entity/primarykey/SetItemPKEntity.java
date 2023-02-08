package logiless.web.model.entity.primarykey;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "API_M_SET_ITEM")
public class SetItemPKEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	@Id
	private String tenpoCode;

}
