package logiless.web.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "API_M_USER")
@Getter
@Setter
public class UserEntity {

	@Id
	private String id;

	private String name;

	private String password;

	private String roles;

}
