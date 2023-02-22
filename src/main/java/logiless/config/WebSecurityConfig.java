package logiless.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import logiless.web.model.service.CustomUserDetailsService;

/**
 * spring security configuration
 * 
 * @author nsh14789
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;

	@Autowired
	public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	// @formatter:off
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((
				requests) -> requests
					.mvcMatchers("/webjars/**").permitAll()
					.mvcMatchers("/css/**").permitAll()
					.mvcMatchers("/login", "/login").permitAll().anyRequest().authenticated()
			)
    		.formLogin((
				form) -> form
    				.loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/", true).permitAll()
    		)
			.logout((
				logout) -> logout
					.logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login").permitAll()
			
			)
			// TODO SecurityFilterChainでの実装理解
//			.exceptionHandling((
//				ex) -> ex
//					.authenticationEntryPoint(new Http403ForbiddenEntryPoint()).accessDeniedHandler(CustomAccessDeniedHandler())
//			)
			;

		return http.build();
	}
	// @formatter:on
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(user);
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Bean
	public UserDetailsManager users(DataSource dataSource) {
		String userQuery = "select name, password, true from api_m_user where name = '?'";
		String authoritiesQuery = "select name, roles from api_m_user where name = '?'";
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setUsersByUsernameQuery(userQuery);
		users.setAuthoritiesByUsernameQuery(authoritiesQuery);
		return users;
	}
//
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		return customUserDetailsService;
//	}
}
