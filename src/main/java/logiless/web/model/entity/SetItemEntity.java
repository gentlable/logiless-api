package logiless.web.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import logiless.web.model.entity.primarykey.SetItemPKEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(SetItemPKEntity.class)
@Table(name = "api_m_set_item")
public class SetItemEntity {

	@Id
	private String code;

	private String name;

	@Id
	private String tenpoCode;

}
