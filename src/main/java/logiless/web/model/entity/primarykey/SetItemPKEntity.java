package logiless.web.model.entity.primarykey;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * セット商品マスタープライマリキーエンティティ
 * 
 * @author nsh14789
 *
 */
@Getter
@Setter
@Entity
public class SetItemPKEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	@Id
	private String tenpoCode;

}
