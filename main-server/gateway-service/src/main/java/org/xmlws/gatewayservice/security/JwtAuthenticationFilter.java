package org.xmlws.gatewayservice.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter 
{
	private TokenUtility tokenUtility;

	public JwtAuthenticationFilter(TokenUtility tokenUtility) {
		this.tokenUtility = tokenUtility;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
								throws IOException, ServletException {
		
		String token = tokenUtility.getToken(request);

		if (token != null) {
			try {
				String username = tokenUtility.getUsernameFromToken(token);
				List<String> authorities = tokenUtility.getAuthoritiesFromToken(token);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
																	username, null, authorities.stream()
																							   .map(SimpleGrantedAuthority::new)
																							   .collect(Collectors.toList()));
				SecurityContextHolder.getContext().setAuthentication(auth);					
			} catch (Exception e) {
				SecurityContextHolder.clearContext();
			}
		}
		
		chain.doFilter(request, response);
	}
}