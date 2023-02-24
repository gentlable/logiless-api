package logiless.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Securityの設定
 * 
 * @author nsh14789
 *
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	// @formatter:off
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((
				requests) -> requests
					.mvcMatchers("/webjars/**").permitAll()
					.mvcMatchers("/css/**").permitAll()
					.mvcMatchers("/login", "/login").permitAll()
					.anyRequest().authenticated()
			)
    		.formLogin((
				form) -> form
    				.loginPage("/login").usernameParameter("username").passwordParameter("password")
    				.failureUrl("/login?error").defaultSuccessUrl("/", true).permitAll()
    		)
			.logout((
				logout) -> logout
					.logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login").permitAll()
			
			)
			// TODO SecurityFilterChainでの実装理解
//			.exceptionHandling((
//				ex) -> ex
//					.authenticationEntryPoint(new Http403ForbiddenEntryPoint()).accessDeniedHandler(new CustomAccessDeniedHandler())
//			)
			;

		return http.build();
	}
	// @formatter:on

	/**
	 * db側で暗号化しているので、java内では行わない
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
