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
			.antMatchers("/accommodation-service/accommodations/{accommodationId}").permitAll()
			.antMatchers("/accommodation-service/accommodations/details/{accommodationId}").permitAll()
			.antMatchers("/accommodation-service/accommodations/{accommodationId}/reservations/{reservationId}").hasAuthority("ROLE_USER")
			.antMatchers("/accommodation-service/accommodations").hasAuthority("ROLE_USER")
			.antMatchers("/accommodation-service/accommodations/{id}/reviews/published").permitAll()
			.antMatchers("/accommodation-service/accommodations/reviews/unpublished").hasAuthority("ROLE_ADMIN")
			.antMatchers("/accommodation-service/accommodations/reviews/average/**").permitAll()
			.antMatchers(HttpMethod.GET, "/accommodation-service/accommodationTypes").permitAll()
			.antMatchers(HttpMethod.GET, "/accommodation-service/additionalService").permitAll()
			.antMatchers(HttpMethod.POST, "/accommodation-service/accommodationTypes").hasAuthority("ROLE_ADMIN")
			.antMatchers(HttpMethod.POST, "/accommodation-service/additionalService").hasAuthority("ROLE_ADMIN")
			.antMatchers(HttpMethod.DELETE, "/accommodation-service/accommodationTypes").hasAnyAuthority("ROLE_ADMIN")
			.antMatchers(HttpMethod.DELETE, "/accommodation-service/additionalService").hasAuthority("ROLE_ADMIN")
			.antMatchers("/registration-service/registration/**").permitAll()
			.antMatchers("/reservation-service/reservations/accommodations/{id}").permitAll()
			.antMatchers("/reservation-service/reservations").hasAuthority("ROLE_USER")
			.antMatchers("/reservation-service/reservations/{id}").hasAuthority("ROLE_USER")
			.antMatchers("/reservation-service/reservations/{id}/users/{username}").hasAuthority("ROLE_USER")
			.antMatchers("/reservation-service/reservations/{id}/message").hasAuthority("ROLE_USER")
			.antMatchers("/reservation-service/reservations/rating").hasAuthority("ROLE_USER")
			.antMatchers("/reservation-service/reservations/{id}/comment").hasAuthority("ROLE_ADMIN")
			.antMatchers("/search-service/search").permitAll()
			.antMatchers("/user-service/admins/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/user-service/users/**").hasAuthority("ROLE_USER")
			.anyRequest().authenticated();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js");
	}
}