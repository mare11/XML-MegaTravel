package org.xmlws.authenticationservice.security;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	public String generateToken(String username, List<String> authorities) {
		return Jwts.builder()
				.setIssuer(appName)
				.setSubject(username)
				.claim(authoritiesClaim, authorities)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiresIn * 1000))
				.signWith(SIGNATURE_ALGORITHM, secret).compact();
	}
}