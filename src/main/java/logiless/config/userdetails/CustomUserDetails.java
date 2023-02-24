package logiless.config.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import logiless.web.model.entity.SgyosyaEntity;

/**
 * ユーザー認証
 * 
 * @author nsh14789
 *
 */
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = -1045509628154498849L;

	private SgyosyaEntity sgyosya;

	public CustomUserDetails(SgyosyaEntity sgyosya) {
		this.sgyosya = sgyosya;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return authorities;
	}

	@Override
	public String getPassword() {
		return sgyosya.getPasswordCd();
	}

	@Override
	public String getUsername() {
		return sgyosya.getSgyosyaCd();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
