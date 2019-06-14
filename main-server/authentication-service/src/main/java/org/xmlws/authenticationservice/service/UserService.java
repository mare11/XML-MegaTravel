package org.xmlws.authenticationservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if (username == null) {
			return null;
		}
		
		User user = (User) loadUser(this.userRepository, username);	
		if (user != null) {
			List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", user.getAuthority().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())));
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities); 							
		} 
		
		Agent agent = (Agent) loadUser(this.agentRepository, username);	
		if (agent != null) {
			List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", agent.getAuthority().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())));
			return new org.springframework.security.core.userdetails.User(agent.getUsername(), agent.getPassword(), authorities); 							
		}
		
		Administrator admin = (Administrator) loadUser(this.adminRepository, username);	
		if (admin != null) {
			List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", admin.getAuthority().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())));
			return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), authorities); 							
		} else {
			throw new UsernameNotFoundException("User with username '" + username + "' is not found.");			
		}
		
	}
	
	private Entity loadUser(ExistXQJRepository<?> repository, String username) {
		
		List<?> entities = repository.findWithFilter("[username='" + username + "']");
		if (!entities.isEmpty()) {
			 return (Entity) entities.get(0);
		} else {
			return null;
		}
	}
}