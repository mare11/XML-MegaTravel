package org.xmlws.gatewayservice.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class TokenUtility {
	
	@Value("/authentication-service/authentication/**")
    private String authPath;

	@Value("Authorization")
	private String authHeader;
	
	@Value("Bearer ")
	private String authPrefix;

	public String getToken(HttpServletRequest request) {
		String authHeaderValue = request.getHeader(authHeader);
		return (authHeaderValue != null && authHeaderValue.startsWith(authPrefix)) ? authHeaderValue.substring(7) : null;
	}
}