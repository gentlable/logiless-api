/*package logiless.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import logiless.web.model.service.DefaultUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig_BK {

	@Autowired
	DefaultUserDetailsService defaultUserDetailsService;

	//@formatter:off
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((
				requests) -> requests.mvcMatchers("/login", "/login").permitAll().anyRequest().authenticated())
			.formLogin((
					form) -> form.loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/", true).permitAll())
			.logout((
					logout) -> logout.logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login").permitAll());

		return http.build();
	}
	//@formatter:on

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = defaultUserDetailsService.loadUserByUsername(null);
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
*/