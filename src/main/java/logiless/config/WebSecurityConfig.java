package logiless.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * spring security configuration
 * 
 * @author nsh14789
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	// @formatter:off
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		final HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		
		http.authorizeHttpRequests((
				requests) -> requests
					.mvcMatchers("/webjars/**").permitAll()
					.mvcMatchers("/css/**").permitAll()
					.mvcMatchers("/login", "/login").permitAll().anyRequest().authenticated()
//					.mvcMatchers("/logout", "/logout").permitAll().anyRequest().authenticated()
			)
    		.formLogin((
				form) -> form
    				.loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/", true).permitAll()
    		)
			.logout((
				logout) -> logout
					.logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login").permitAll()
			
			)
//			.csrf().csrfTokenRepository(repository)
//			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			;

		return http.build();
	}
	// @formatter:on

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}

}
