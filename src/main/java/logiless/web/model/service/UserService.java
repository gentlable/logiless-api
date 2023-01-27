package logiless.web.model.service;

import org.springframework.beans.factory.annotation.Autowired;

import logiless.web.model.dto.User;
import logiless.web.model.repository.UserRepository;

public class UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * ユーザー登録用API
	 */
	public User createUser;

}
