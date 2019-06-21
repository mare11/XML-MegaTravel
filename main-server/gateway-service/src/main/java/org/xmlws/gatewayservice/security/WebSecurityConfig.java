package org.xmlws.gatewayservice.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private TokenUtility tokenUtility;
	
	@Autowired
    private WebClient.Builder webClientBuilder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling().authenticationEntryPoint(
					(request, response, exception) -> 
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage()
					))
			.and()
			//Intercept every request to validate token
			.addFilterAfter(new JwtAuthenticationFilter(tokenUtility, webClientBuilder), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			//Allow anyone to access authentication endpoint
			.antMatchers(tokenUtility.getAuthPath()).permitAll()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js");
	}
}