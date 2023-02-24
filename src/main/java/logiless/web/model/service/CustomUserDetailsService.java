package logiless.web.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import logiless.config.userdetails.CustomUserDetails;
import logiless.web.model.entity.SgyosyaEntity;
import logiless.web.model.repository.SgyosyaRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final SgyosyaRepository sgyosyaRepository;

	@Autowired
	public CustomUserDetailsService(SgyosyaRepository sgyosyaRepository) {
		this.sgyosyaRepository = sgyosyaRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String sgyosyaCd) throws UsernameNotFoundException {
		SgyosyaEntity user = sgyosyaRepository.findDecryptedPasswordCdBySgyosyaCd(sgyosyaCd);

		if (user == null) {
			throw new UsernameNotFoundException("Not Found" + sgyosyaCd);
		}

		return new CustomUserDetails(user);
	}

}
