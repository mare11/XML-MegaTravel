package org.xmlws.gatewayservice.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;

@Getter
@Component
public class TokenUtility {
	
	@Value("mega-travel")
	private String appName;
	
	@Value("/authentication/**")
    private String authPath;

	@Value("Authorization")
	private String authHeader;
	
	@Value("Bearer ")
	private String authPrefix;

	@Value("JwtSecretKey")
	private String secret;
	
	@Value("authorities")
	private String authoritiesClaim;

	@Value("86400")
	private int expiresIn;

	private Claims getClaimsFromToken(String token) throws Exception {
		return Jwts.parser()
				   .setSigningKey(secret)
				   .parseClaimsJws(token)
				   .getBody();
	}

	public String getUsernameFromToken(String token) throws Exception {	
		Claims claims = this.getClaimsFromToken(token);
		return claims.getSubject();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAuthoritiesFromToken(String token) throws Exception {
		Claims claims = this.getClaimsFromToken(token);
		return (List<String>) claims.get(authoritiesClaim);
	}

	public String getToken(HttpServletRequest request) {
		String authHeaderValue = request.getHeader(authHeader);
		return (authHeaderValue != null && authHeaderValue.startsWith(authPrefix)) ? authHeaderValue.substring(7) : null;
	}
}