//package logiless.web.model.service;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import logiless.web.model.entity.UserEntity;
//import logiless.web.model.repository.UserRepository;
//
//@Service
//public class DefaultUserDetailsService implements UserDetailsService {
//
//	@Autowired
//	UserRepository userRepository;
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserEntity user = userRepository.findById(username)
//				.orElseThrow(() -> new UsernameNotFoundException("user not found"));
//
//		Collection<GrantedAuthority> authority = Arrays.stream(user.getRoles().split(","))
//				.map((role) -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
//
//		return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), authority);
//	}
//
//}
