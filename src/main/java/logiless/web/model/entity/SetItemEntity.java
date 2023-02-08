package logiless.web.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import logiless.web.model.entity.primarykey.SetItemPKEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(SetItemPKEntity.class)
public class SetItemEntity {

	@Id
	private String code;

	private String name;

	@Id
	private String tenpoCode;

}
