package org.xmlws.authenticationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.xmlws.authenticationservice.exceptions.EmailNullPointerException;
import org.xmlws.authenticationservice.exceptions.UsernameNullPointerException;
import org.xmlws.authenticationservice.model.Administrator;
import org.xmlws.authenticationservice.model.Agent;
import org.xmlws.authenticationservice.model.User;
import org.xmlws.authenticationservice.repository.AdministratorRepository;
import org.xmlws.authenticationservice.repository.AgentRepository;
import org.xmlws.authenticationservice.repository.UserRepository;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.Entity;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private AdministratorRepository adminRepository;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UsernameNullPointerException {
		
		if (username == null) {
			throw new UsernameNullPointerException();
		}
		
		User user = (User) loadByProperty(this.userRepository, "username", username);	
		if (user != null) {
//			List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", user.getAuthority().stream().map(Authority::getAuthority).collect(Collectors.toList())));
//			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
			return user;
		} 
		
		Agent agent = (Agent) loadByProperty(this.agentRepository, "username", username);	
		if (agent != null) {
//			List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", agent.getAuthority().stream().map(Authority::getAuthority).collect(Collectors.toList())));
//			return new org.springframework.security.core.userdetails.User(agent.getUsername(), agent.getPassword(), authorities);
			return agent;
		}
		
		Administrator admin = (Administrator) loadByProperty(this.adminRepository, "username", username);	
		if (admin != null) {
//			List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", admin.getAuthority().stream().map(Authority::getAuthority).collect(Collectors.toList())));
//			return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), authorities);
			return admin;
		} else {
			throw new UsernameNotFoundException("User with username '" + username + "' is not found.");			
		}
	}
	
	public boolean checkEmail(String email) throws UsernameNotFoundException, UsernameNullPointerException {
		
		if (email == null) {
			throw new EmailNullPointerException();
		}
			
		if (loadByProperty(this.userRepository, "email", email) != null || loadByProperty(this.agentRepository, "email", email) != null) {
			return false;   							
		} 
		
		return true;
	}
	
	private Entity loadByProperty(ExistXQJRepository<?> repository, String propertyName, String value) {
		
		List<?> entities = repository.findWithFilter("[" + propertyName + "='" + value + "']");
		if (!entities.isEmpty()) {
			 return (Entity) entities.get(0);
		} else {
			return null;
		}
	}
	
	public String generatePassword(String password) {
		return this.passwordEncoder.encode(password);
	}
}