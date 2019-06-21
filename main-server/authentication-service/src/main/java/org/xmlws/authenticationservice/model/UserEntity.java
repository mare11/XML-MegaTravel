package org.xmlws.authenticationservice.model;

import org.springframework.security.core.userdetails.UserDetails;
import org.xmlws.dataservice.entity.Entity;

public abstract class UserEntity extends Entity implements UserDetails {

	private static final long serialVersionUID = -5206001771837013081L;

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