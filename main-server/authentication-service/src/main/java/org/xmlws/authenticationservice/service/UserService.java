package org.xmlws.authenticationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.xmlws.authenticationservice.model.User;
import org.xmlws.authenticationservice.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> users;
		users = this.userRepository.findWithFilter("[username='" + username + "']");
		if (!users.isEmpty()) {
			User user = users.get(0);
			List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", user.getAuthorities()));
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities); 				
		}
		else {
			throw new UsernameNotFoundException("User with username '" + username + "' is not found.");	
		}
	}
}